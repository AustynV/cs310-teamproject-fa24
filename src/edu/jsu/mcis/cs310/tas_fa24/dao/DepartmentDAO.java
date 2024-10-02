package edu.jsu.mcis.cs310.tas_fa24.dao;

import edu.jsu.mcis.cs310.tas_fa24.Department;

/**
 *
 * @author David
 */
public class DepartmentDAO {
    /*
    +----+--------------+------------+
    | id | description  | terminalid |
    +----+--------------+------------+
    |  1 | Assembly     |        103 |
    |  2 | Cleaning     |        107 |
    |  3 | Warehouse    |        106 |
    |  4 | Grinding     |        104 |
    |  5 | Hafting      |        105 |
    |  6 | Office       |        102 |
    |  7 | Press        |        104 |
    |  8 | Shipping     |        107 |
    |  9 | Tool and Die |        104 |
    | 10 | Maintenance  |        104 |
    +----+--------------+------------+
    */
    
    private static final String QUERY_FIND = "SELECT * FROM  WHERE id = ?";


    private final DAOFactory daoFactory;

    DepartmentDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    
    public Department find(int id){
        return null;
    }
}
