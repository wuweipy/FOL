package Data.Common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Common.FOLLogger;
import Data.DBHandler.DBConnectorFactory;

public class ProductDAO implements IProductDAO
{

	private static Logger logger = FOLLogger.getLogger(ProductDAO.class);
	
	public List<DProduct> getProductList() 
	{
			Connection connection = DBConnectorFactory.getConnectorFactory().getConnection();
			List<DProduct> prudList = new ArrayList<DProduct>();
			DProduct product = null;
			if(connection == null)
			{
				return prudList;
			}
			Statement statement = null;
			ResultSet rs = null;
			try 
			{
				statement = connection.createStatement();
			    rs = statement.executeQuery("SELECT * from DEPT;");
				while (rs.next()) 
				{
					product = new DProduct();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					prudList.add(product);
			    }
			}
			catch (SQLException e) 
			{
				logger.error(e.getMessage());
			}
			finally
			{
				DBConnectorFactory.getConnectorFactory().freeDB(connection, statement, rs);
			}
			return prudList;
	}
	
	public static void main(String[] args) {
		ProductDAO daoProductDAO = new ProductDAO();
		System.out.println(daoProductDAO.getProductList().get(0).getName());
	}

}
