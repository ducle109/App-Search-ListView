package leduc.com.hocmenucontext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import leduc.com.adapter.StudentAdapter;
import leduc.com.model.Student;

public class MainActivity extends AppCompatActivity {
    private ListView lvStudent;
    private StudentAdapter studentAdapter;
    private Student selectedStudent = null;
    private List<Student> studentList = new ArrayList<Student>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addEvents();
    }

    // hien thi context
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // su li icons context
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addNewItem) {
            showMenuAdd();
        } else if(item.getItemId() == R.id.deleteItem) {
            deleteMenuItem();
        }
        return super.onContextItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.demo_menu, menu);
        MenuItem menuSearch = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()) {
                    studentAdapter.clear();
                    studentAdapter.addAll(studentList);
                } else {
                    List<Student> newStudent = new ArrayList<Student>();

                    for(Student st : studentList) {
                        if(st.getCode().contains(s) || st.getName().contains(s)) {
                            newStudent.add(st);
                        }
                    }
                    studentAdapter.clear();
                    studentAdapter.addAll(newStudent);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuAdd:
                addStudent();
                break;
            case R.id.menuNew:
                showToast("hello new menu desu");
                break;
            case R.id.menuSearch:
                showToast("hello search menu desu");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addStudent() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_student);
        final EditText edtCode = (EditText) dialog.findViewById(R.id.edtCode);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);

        final RadioButton rdButtonMan     = (RadioButton) dialog.findViewById(R.id.rdMale);
        final RadioButton rdButtonWoman   = (RadioButton) dialog.findViewById(R.id.rdFamale);
        final Button btnSave      = (Button) dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setCode(edtCode.getText().toString());
                student.setName(edtName.getText().toString());
                student.setSex(rdButtonMan.isChecked());

                studentAdapter.add(student);
                studentList.clear();
                for(int i = 0; i < studentAdapter.getCount(); i++) {
                    studentList.add(studentAdapter.getItem(i));
                }
                dialog.dismiss();
            }


        });
        dialog.show();
    }

    private void showMenuAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_student);
        final EditText edtCode = (EditText) dialog.findViewById(R.id.edtCode);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);

        final RadioButton rdButtonMan     = (RadioButton) dialog.findViewById(R.id.rdMale);
        final RadioButton rdButtonWoman   = (RadioButton) dialog.findViewById(R.id.rdFamale);
        final Button btnSave      = (Button) dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedStudent.setCode(edtCode.getText().toString());
                selectedStudent.setName(edtName.getText().toString());
                selectedStudent.setSex(rdButtonMan.isChecked());

                studentAdapter.notifyDataSetChanged();
                studentList.clear();
                for(int i = 0; i < studentAdapter.getCount(); i++) {
                    studentList.add(studentAdapter.getItem(i));
                }
                dialog.dismiss();
            }
        });
        edtCode.setText(selectedStudent.getCode());
        edtName.setText(selectedStudent.getName());
        if(selectedStudent.getSex()) {
            rdButtonMan.setChecked(true);
        } else {
            rdButtonWoman.setChecked(true);
        }
        dialog.show();
    }
    private void deleteMenuItem() {
        studentAdapter.remove(selectedStudent);
    }

    public void addEvents() {
        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedStudent = studentAdapter.getItem(position);
            }
        });

        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedStudent = studentAdapter.getItem(position);
                return false;
            }
        });
    }

    public void init() {
        lvStudent = (ListView) findViewById(R.id.lvStudent);
        studentAdapter = new StudentAdapter(this, R.layout.layout_item);
        lvStudent.setAdapter(studentAdapter);

        registerForContextMenu(lvStudent);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
