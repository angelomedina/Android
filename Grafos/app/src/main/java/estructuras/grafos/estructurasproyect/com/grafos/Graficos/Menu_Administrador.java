package estructuras.grafos.estructurasproyect.com.grafos.Graficos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import estructuras.grafos.estructurasproyect.com.grafos.R;

public class Menu_Administrador extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__administrador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__administrador, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.consultaUno) {
            Toast.makeText(Menu_Administrador.this, "Presionastes camino mas corto", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.consultaDos)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes tipo de grafo(Conexo)", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.consultaTres)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes tipo de grafo(Cíclico)", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.consultaCuatro)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes verificacion de camino", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.reporteUno)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes Vértices sumideros", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.reporteDos)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes Vértices fuentes", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.reporteTres)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes Grado Interno", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.reporteCuatro)
        {
            Toast.makeText(Menu_Administrador.this, "Presionastes Grado Externo", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
