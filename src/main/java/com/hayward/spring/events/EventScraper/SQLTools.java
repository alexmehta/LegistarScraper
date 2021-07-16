/*
 */

package com.hayward.spring.events.EventScraper;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLTools {
    Connection conn = null;
    Connection setConnection() throws SQLException;

}
