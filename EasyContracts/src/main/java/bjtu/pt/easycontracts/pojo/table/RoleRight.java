package bjtu.pt.easycontracts.pojo.table;

public class RoleRight extends RoleRightKey {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}