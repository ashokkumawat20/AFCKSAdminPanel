package in.afckstechnologies.afcksadminpanel.models;

public class UserModel {
    String id = "";
    String first_name = "";
    String last_name = "";
    String mobile_no = "";
    String email_id = "";
    String gender = "";
    String fees_options_status = "";
    String shift_id = "";
    String wfh_applicable_status = "";
    String faculty_flag = "";
    String yearly_leaves = "";

    public UserModel() {

    }

    public UserModel(String id, String first_name, String last_name, String mobile_no, String email_id, String gender, String fees_options_status, String shift_id, String wfh_applicable_status, String faculty_flag, String yearly_leaves) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.gender = gender;
        this.fees_options_status = fees_options_status;
        this.shift_id = shift_id;
        this.wfh_applicable_status = wfh_applicable_status;
        this.faculty_flag = faculty_flag;
        this.yearly_leaves = yearly_leaves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFees_options_status() {
        return fees_options_status;
    }

    public void setFees_options_status(String fees_options_status) {
        this.fees_options_status = fees_options_status;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
    }

    public String getWfh_applicable_status() {
        return wfh_applicable_status;
    }

    public void setWfh_applicable_status(String wfh_applicable_status) {
        this.wfh_applicable_status = wfh_applicable_status;
    }

    public String getFaculty_flag() {
        return faculty_flag;
    }

    public void setFaculty_flag(String faculty_flag) {
        this.faculty_flag = faculty_flag;
    }

    public String getYearly_leaves() {
        return yearly_leaves;
    }

    public void setYearly_leaves(String yearly_leaves) {
        this.yearly_leaves = yearly_leaves;
    }
}
