package com.example.linxj.xmlpull;


import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.linxj.adapter.MyAdapter;
import com.example.linxj.adapter.RecommendAdapter;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    //private HashMap<String, String> map = new HashMap<String, String>();
    private String url = "http://api.douban.com/book/subject/isbn/9787543639133?alt=json";
    private RecyclerView recyclerView;
    private  MyAdapter myAdapter;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ViewPager mRecommendPager;
    private RecommendAdapter mRecommendAdapter;
    private CollapsingToolbarLayout collapsingToolbar;
    private FloatingActionButton fab;
    StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initToolbar();
        initNavigationView();
        initViewPage();
        initFloationButton();
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("CSDC");
        try {
            InputStream is = getResources().getAssets().open("book.xml");
            BookLab lab = new BookXmlClass().parse(is);
            ArrayList<Book> books = lab.getBooks();
            Iterator<Book> iterator = books.iterator();
            //map.clear();
            recyclerView = (RecyclerView)super.findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            myAdapter = new MyAdapter(this, books);
            recyclerView.setAdapter(myAdapter);
            myAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(MainActivity.this,"wwww",Toast.LENGTH_LONG).show();
                }
            });
           /* new Thread(){
                @Override
                public void run() {
                    String result = new NetAssiant().get();
                }
            }.start();*/
            while(iterator.hasNext()){
                Book b = iterator.next();
                Log.e("book info", b.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("图书管理系统");

        toolbar.setSubtitle("CSDC");
        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
        toolbar.setOnMenuItemClickListener(this);
    }

    public void initNavigationView(){
        navigationView = (NavigationView)findViewById(R.id.navigationView);
       drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_blog:
                        //startActivity(new Intent(MainActivity.this, BlogActivity.class));
                        break;
                    case R.id.nav_ver:
                        break;
                    case R.id.nav_about:
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void initViewPage(){

        mRecommendPager = (ViewPager)findViewById(R.id.pager);
        mRecommendPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF downP = new PointF();
                PointF curP = new PointF();
                int act = event.getAction();
                if (act == MotionEvent.ACTION_DOWN
                        || act == MotionEvent.ACTION_MOVE
                        || act == MotionEvent.ACTION_UP) {
                    ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                    if (downP.x == curP.x && downP.y == curP.y) {
                        return false;
                    }
                }
                return false;
            }
        });
        mRecommendPager.setAdapter(mRecommendAdapter);
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        View view1=mLayoutInflater.inflate(R.layout.guide_one, null);
        View view2=mLayoutInflater.inflate(R.layout.guide_two, null);
        View view3=mLayoutInflater.inflate(R.layout.guide_end, null);

        final ArrayList<View> views =new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mRecommendAdapter = new RecommendAdapter(views,this);
        mRecommendPager.setAdapter(mRecommendAdapter);
    }
    public void initFloationButton(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(v, "弹出snackbar", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "SnackBar action", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(this, "���Ұ�ť", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(this, "���?ť", Toast.LENGTH_SHORT).show();
                break;
        }

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
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
