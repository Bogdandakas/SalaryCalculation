package com.vcs.bogdan.service.db;

import com.vcs.bogdan.beans.LogHandler;
import java.util.logging.Logger;

public interface ConnectionService {

    boolean isDefaultConnection();

    void connect();

}
