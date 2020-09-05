package in.afckstechnologies.afcksadminpanel.models;

public class AppsModels {
    String role_id="";
    String role_desc="";
    String checked="";
    private boolean isSelected;

    public AppsModels() {

    }

    public AppsModels(String role_id, String role_desc, String checked, boolean isSelected) {
        this.role_id = role_id;
        this.role_desc = role_desc;
        this.checked = checked;
        this.isSelected = isSelected;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_desc() {
        return role_desc;
    }

    public void setRole_desc(String role_desc) {
        this.role_desc = role_desc;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
