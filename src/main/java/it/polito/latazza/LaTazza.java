package it.polito.latazza;

import java.sql.SQLException;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.data.DataInterface;
import it.polito.latazza.gui.MainSwing;

public class LaTazza {

	public static void main(String[] args) throws SQLException {
		DataInterface data = new DataImpl();
		new MainSwing(data);
	}

}
