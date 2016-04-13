package target.samarthanam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by z081942 on 4/9/16.
 */
public class Tasks {

    public String _id;
    public String task;
    public String created_by;
    public String assigned_to;
    public String status;
    public String created_date;
    public String expected_completion_date;
    public String __v;

    public Tasks(JSONObject jsonObject) {
            try {
                this._id = jsonObject.getString("_id");
                this.task = jsonObject.getString("task");
                this.created_by = jsonObject.getString("created_by");
                this.assigned_to = jsonObject.getString("assigned_to");
                this.status = jsonObject.getString("status");
                this.created_date = jsonObject.getString("created_date");
                this.expected_completion_date = jsonObject.getString("expected_completion_date");
                this.__v = jsonObject.getString("__v");

            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public static ArrayList<Tasks> fromJson(JSONArray jsonObjects) {
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                tasks.add(new Tasks(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public static String createTasktext(JSONArray jsonObjects) {
        String taskText="Tasks details are :";
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                taskText = taskText + "Task number " + (i+1) + jsonObjects.getJSONObject(i).getString("task") + " created by " + jsonObjects.getJSONObject(i).getString("created_by") + " assigned to " + jsonObjects.getJSONObject(i).getString("assigned_to");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return taskText;
    }

}
