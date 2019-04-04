package jrds.agent.windows.pdh;

public class PdhHelper {

    private PdhHelper() {}

    // \\computer\object(parent/instance#index)\counter
    public static String constructPdhPath(String computer, String object, String parent, String instance,
                                          Integer index, String counter) {
        if (counter == null || counter.equals("")) {
            throw new IllegalArgumentException("counter parameter can not be null or empty");
        }
        String computerPart = computer.equals("") ? "" : "\\\\" + computer;
        String instancePart = instance.equals("") ? ""
                                                    : "(" + (parent.equals("") ? "" : parent + "/") + instance
                                                    + (index == null ? "" : "#" + index) + ")";
        return computerPart + "\\" + object + instancePart + "\\" + counter;
    }

    // \\computer\object(parent/instance)\counter
    public static String constructPdhPath(String computer, String object, String parent, String instance,
                                          String counter) {
        return constructPdhPath(computer, object, parent, instance, null, counter);
    }

    // \\computer\object(instance#index)\counter
    public static String constructPdhPath(String computer, String object, String instance, Integer index,
                                          String counter) {
        return constructPdhPath(computer, object, "", instance, index, counter);
    }

    // \\computer\object(instance)\counter
    public static String constructPdhPath(String computer, String object, String instance,
                                          String counter) {
        return constructPdhPath(computer, object, "", instance, null, counter);
    }

    // \object(instance#index)\counter
    public static String constructPdhPath(String object, String instance, Integer index,
                                          String counter) {
        return constructPdhPath("", object, "", instance, index, counter);
    }

    // \object(instance)\counter
    public static String constructPdhPath(String object, String instance,
                                          String counter) {
        return constructPdhPath("", object, "", instance, null, counter);
    }

    // \object\counter
    public static String constructPdhPath(String object, String counter) {
        return constructPdhPath("", object, "", "", null, counter);
    }

}
