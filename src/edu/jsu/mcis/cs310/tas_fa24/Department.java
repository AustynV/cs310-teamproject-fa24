package edu.jsu.mcis.cs310.tas_fa24;

/**
 *
 * @author David
 */
public class Department {

    private final String description;
    private final int id;
    private final int terminalid;
    //test
    String num = "#";
    String termId = ", Terminal ID: ";
    
    public Department(int id, String description, int terminalid) {
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
    
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public int getTerminalid(){
        return terminalid;
    }

}
    
