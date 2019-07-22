package leduc.com.model;

public class Student {
    private String Code;
    private String Name;
    private Boolean Sex;

    public Student() {
    }

    public Student(String code, String name, Boolean sex) {
        Code = code;
        Name = name;
        Sex = sex;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }
}
