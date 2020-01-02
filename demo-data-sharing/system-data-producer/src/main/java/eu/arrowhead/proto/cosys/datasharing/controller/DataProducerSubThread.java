package eu.arrowhead.proto.cosys.datasharing.controller;


import eu.arrowhead.common.dto.shared.EventDTO;
import eu.arrowhead.proto.cosys.datasharing.DataProviderConstants;
import eu.arrowhead.proto.cosys.datasharing.database.InMemoryDb;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DataProducerSubThread extends Thread {
    private boolean interrupted = false;

    @Resource(name = DataProviderConstants.NOTIFICATION_QUEUE)
    private ConcurrentLinkedQueue<EventDTO> notificationQueue;

    @Resource(name = DataProviderConstants.THRESHOLD)
    private Integer threshold;

    @Resource(name = DataProviderConstants.IN_MEMORY_DB)
    private InMemoryDb inMemoryDb;

    @Override
    public void run() {
        while(!interrupted) {
            try {
                if (notificationQueue.peek() != null) {
                    for (final EventDTO event : notificationQueue) {
                        if (event.getEventType().equals(DataProviderConstants.REQUEST_RECEIVED)) {
                            if (Integer.parseInt(event.getMetaData().get("Offer")) >= this.threshold) {
                                // save the offer
                                String randomIdentifier = event.getMetaData().get(DataProviderConstants.REQUEST_RANDOM_IDENTIFIER);
                                String hash = event.getMetaData().get("Hash");
                                inMemoryDb.getOfferMap().put(randomIdentifier, hash);
                            }
                        } else if (event.getEventType().equals(DataProviderConstants.REQUEST_STOP)) {
                            interrupted = true;
                        }
                    }
                    notificationQueue.clear();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

