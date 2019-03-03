package sandkev.dogs;

import java.util.UUID;

/**
 * Created by kevsa on 02/03/2019.
 */
public class EventGenerator {


    public static void main(String[] args) {

        WorkflowSummary workflowSummary = new WorkflowSummary.WorkflowSummaryBuilder()
                .workflowId(UUID.randomUUID().toString())
                .batch(
                        new Batch.BatchBuilder()
                        .name("LONDON_CORE")
                        .riskGroup("EODLONFO1")
                        .build()
                )
                .valueDate(20190301)
                .startTime(System.currentTimeMillis())
                .status("STARTED")
                .build();



    }



}
