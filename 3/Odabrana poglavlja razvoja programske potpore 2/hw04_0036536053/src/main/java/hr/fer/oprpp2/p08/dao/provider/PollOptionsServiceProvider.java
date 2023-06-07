package hr.fer.oprpp2.p08.dao.provider;

import hr.fer.oprpp2.p08.dao.sql.SQLPollOptionsServiceImpl;
import hr.fer.oprpp2.p08.services.PollOptionsServiceI;

public class PollOptionsServiceProvider {

    private static PollOptionsServiceI pollOptionsService = new SQLPollOptionsServiceImpl();

    public static PollOptionsServiceI getPollOptionsService() {
        return pollOptionsService;
    }
    
}
