package edu.jsu.mcis.cs310.tas_fa24;

/**
 *
 * @author David
 */
public class Department {

    private final String description;
    private final String id;
    private final String terminalid;
    //test
    String num = "#";
    String termId = ", Terminal ID: ";
    
    public Department(String id, String description, String terminalid) {
        this.id = id;
        this.description = description;    
        this.terminalid = terminalid;

    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(num);
        sb.append(id).append(" ");
        sb.append("(").append(description).append(")");
        sb.append(termId);
        sb.append(terminalid);
        return sb.toString();
    }

    }
    
}
