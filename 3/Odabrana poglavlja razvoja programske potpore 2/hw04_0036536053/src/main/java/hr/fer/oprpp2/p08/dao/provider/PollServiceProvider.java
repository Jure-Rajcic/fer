package hr.fer.oprpp2.p08.dao.provider;

import hr.fer.oprpp2.p08.dao.sql.SQLPollServiceImpl;
import hr.fer.oprpp2.p08.services.PollsServicesI;

public class PollServiceProvider {
    
        private static PollsServicesI pollService = new SQLPollServiceImpl();
        
        public static PollsServicesI getPollService() {
            return pollService;
        }
    
}
