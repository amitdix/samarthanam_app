package target.samarthanam;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static target.samarthanam.Tasks.createTasktext;

public class NewsHome extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static File newsfile;
    TableLayout tl;
    TableRow tr;
    TextView companyTV,valueTV;
    TextToSpeech t1;
    private Handler viewHandler;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
//    public final static String alltasksURL = "http://192.168.1.34:8000/tasks/api/v1/tasks";
    public final static String alltasksURL = "http://10.93.113.194:8000/tasks/api/v1/tasks";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        ArrayList<Tasks> arrayOfTasks = null;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String result="";
            View rootView = inflater.inflate(R.layout.fragment_news_home, container, false);
            tl = (TableLayout) rootView.findViewById(R.id.maintable);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
                textView.setText("News about t20 Worldcup");
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                textView.setText("Allocate Tasks");

                tr = new TableRow(getContext());
                final EditText editTask = new EditText(getContext());
                editTask.setText("type tasks details here");
                //editTask.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                editTask.setSingleLine(false);
                editTask.setLines(5);
                editTask.setVerticalScrollBarEnabled(true);
                editTask.setTextSize(12);
                editTask.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), 2);
                editTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTask.setText("");
                    }
                });

                tr.addView(editTask);
                tr.setPadding(0, 20, 0, 20);
                tl.addView(tr);

                tr = new TableRow(getContext());
                final EditText editAssign = new EditText(getContext());
                editAssign.setText("Assign task to");
                editAssign.setTextSize(12);
                editAssign.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editAssign.setText("");
                    }
                });
                tr.addView(editAssign);
                tr.setPadding(0, 20, 0, 20);
                tl.addView(tr);

                tr = new TableRow(getContext());
                EditText editETA = new EditText(getContext());
                editETA.setText("Type date in Date-Month-Year Format");
                editETA.setTextSize(12);
                tr.addView(editETA);
                tr.setPadding(0, 20, 0, 20);
                tl.addView(tr);
                tr = new TableRow(getContext());
                tr.setMinimumHeight(10);
                tr.setMinimumWidth(10);

                Button submit = new Button(getContext());
                submit.setTextSize(12);
                submit.setMaxWidth(10);
                submit.setText("Create");
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taskstring= "{\n" +
                                "        \"task\": \" " +
                                editTask.getText() +
                                "\",\n" +
                                "        \"created_by\": \" " +
                                editAssign.getText() +
                                "\",\n" +
                                "        \"assigned_to\": \"" +
                                editAssign.getText() +
                                "\",\n" +
                                "        \"status\": \"open\",\n" +
                                "        \"created_date\": \"03/04/2016\",\n" +
                                "        \"expected_completion_date\": \"03/03/2016\"\n" +
                                "    }";
                        try
                        {
                            JSONObject task = new JSONObject(taskstring);
                            CallAPI obj = new CallAPI();
                            String taskUrl = "http://10.93.113.194:8000/tasks/api/v1/tasks";
                            AsyncTask<String, String, String> execute = obj.execute(taskUrl, "POST", taskstring);
                            String result = execute.get();

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                       catch (InterruptedException e)
                       {
                            e.printStackTrace();
                       }
                        catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }

                }
                });

                tr.addView(submit);
                tr.setPadding(0,20,0,20);
                tl.addView(tr);
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3)
            {
                textView.setText("View All Tasks");
                String urlString = alltasksURL;
                try {
                    CallAPI obj = new CallAPI();
                    AsyncTask<String, String, String> execute = obj.execute(urlString, "GET");
                    result = execute.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result = "";
                } catch (ExecutionException e) {
                    result = "";
                    e.printStackTrace();
                }
                //Toast.makeText(getContext(), "result " + result, Toast.LENGTH_SHORT).show();
                try
                {
                    arrayOfTasks = Tasks.fromJson(new JSONArray(result));
                    String toSpeak;
                    if (t1 != null && createTasktext(new JSONArray(result)) != null) {

                        toSpeak = createTasktext(new JSONArray(result));
                        t1.setSpeechRate((float) 0.8);
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, "1");
                        //Toast.makeText(getContext(), toSpeak, Toast.LENGTH_LONG).show();
                    }

                    t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                t1.setLanguage(Locale.UK);
                                t1.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                                    @Override
                                    public void onStart(String utteranceId) {

                                    }

                                    @Override
                                    public void onDone(String utteranceId) {
                                        Runnable run = new Runnable() {
                                            public void run() {
                                            }
                                        };
                                        viewHandler.post(run);
                                    }

                                    @Override
                                    public void onError(String utteranceId) {

                                    }
                                });
                            }
                        }
                    });
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                addHeaders(arrayOfTasks);
                addData(arrayOfTasks);
            }


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new PlaceholderFragment().newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "World blind cricket NEWS";
                case 1:
                    return "Allocate Tasks";
                case 2:
                    return "Show all Tasks";
            }


            return null;
        }
    }

    /** This function add the headers to the table **/
    public void addHeaders(ArrayList<Tasks> arrayOfTasks){
        /** Create a TableRow dynamically **/

            tr = new TableRow(this);

            /** Creating a TextView to add to the row **/
            TextView taskd_description = new TextView(this);
            taskd_description.setText("tasks");
            taskd_description.setPadding(5, 5, 5, 0);
            taskd_description.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(taskd_description);  // Adding textView to tablerow.

            /** Creating another textview **/
            TextView created_by = new TextView(this);
            created_by.setText("created by");
            created_by.setPadding(5, 5, 5, 0);
            created_by.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(created_by); // Adding textView to tablerow.

            /** Creating another textview **/
            TextView assigned_to = new TextView(this);
            assigned_to.setText("assigned to");
            assigned_to.setPadding(5, 5, 5, 0);
            assigned_to.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(assigned_to); // Adding textView to tablerow.

            /** Creating another textview **/
            TextView expected_completion_date = new TextView(this);
            expected_completion_date.setText("ETA");
            expected_completion_date.setPadding(5, 5, 5, 0);
            expected_completion_date.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(expected_completion_date); // Adding textView to tablerow.

            /** Creating another textview **/
            TextView status = new TextView(this);
            status.setText("status");
            status.setPadding(5, 5, 5, 0);
            status.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(status); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr);
            // we are adding two textviews for the divider because we have two columns
            tr = new TableRow(this);
            // Add the TableRow to the TableLayout
            tl.addView(tr);

    }

    /** This function add the data to the table **/
    public void addData(ArrayList<Tasks> arrayOfTasks){

        if(arrayOfTasks != null )
        {
            if( arrayOfTasks.size() > 0) {
                for (int i = 0; i < arrayOfTasks.size(); i++) {
                    /** Create a TableRow dynamically **/
                    tr = new TableRow(this);
                    tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

                    /** Creating a TextView to add to the row **/
                    TextView taskd_description = new TextView(this);
                    taskd_description.setText(arrayOfTasks.get(i).task);
                    taskd_description.setPadding(5, 5, 5, 5);
                    tr.addView(taskd_description);  // Adding textView to tablerow.

                    /** Creating another textview **/
                    TextView created_by = new TextView(this);
                    created_by.setText(arrayOfTasks.get(i).created_by);
                    created_by.setPadding(5, 5, 5, 5);
                    tr.addView(created_by); // Adding textView to tablerow.

                    TextView assigned_to = new TextView(this);
                    assigned_to.setText(arrayOfTasks.get(i).assigned_to);
                    assigned_to.setPadding(5, 5, 5, 5);
                    tr.addView(assigned_to); // Adding textView to tablerow.

                    TextView expected_completion_date = new TextView(this);
                    expected_completion_date.setText(arrayOfTasks.get(i).expected_completion_date);
                    expected_completion_date.setPadding(5, 5, 5, 5);
                    tr.addView(expected_completion_date); // Adding textView to tablerow.

                    TextView status = new TextView(this);
                    status.setText(arrayOfTasks.get(i).status);
                    status.setPadding(5, 5, 5, 5);
                    tr.addView(status); // Adding textView to tablerow.

                    // Add the TableRow to the TableLayout
                    tl.addView(tr);
                }
            }
        }
    }
}


