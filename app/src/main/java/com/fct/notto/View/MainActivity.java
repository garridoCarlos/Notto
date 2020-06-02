package com.fct.notto.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.fct.notto.R;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener /*, Fragment_login.LoginListener */ {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;
    private SharedPreferences prefsEdit;
    private SharedPreferences prefsLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Cargar el tema principal
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Carga el componente de navegación al inciar la aplicacion.
        setupNavigation();

        prefsLogged = getSharedPreferences("logged", MODE_PRIVATE);
        boolean isLogged = prefsLogged.getBoolean("isLogged", false);

        //Inicia la aplicación en la pantalla de login si el estado es "no logueado".
        if (!isLogged) {
            navController.navigate(R.id.action_fragment_board_to_fragment_login);
        }

    }

    //Establece estado "no logueado" al cerrar la sesión.
    public void logout() {
        prefsLogged = getSharedPreferences("logged", MODE_PRIVATE);
        SharedPreferences.Editor editorLogged = prefsLogged.edit();
        editorLogged.putBoolean("isLogged", false).apply();
    }

    // Cargar el componente de navegación y habilitar la flecha de retroceso.
    private void setupNavigation() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Menú de la barra superior
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.closeSession) {
            logout();
            navController.navigate(R.id.action_global_fragment_login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Botón de flecha para retroceder pantalla
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    //Cierra el menú lateral al pulsar retroceder.
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Botones del menú lateral.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.user:
                navController.navigate(R.id.action_fragment_board_to_fragment_user);
                break;
            case R.id.addnote:
                prefsEdit = getSharedPreferences("edit", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorEdit = prefsEdit.edit();
                editorEdit.putBoolean("editMode", false).apply();
                navController.navigate(R.id.action_fragment_board_to_fragment_notes);
                break;
            case R.id.adddraw:
                navController.navigate(R.id.action_fragment_board_to_fragment_canvas);
                break;
        }
        return true;

    }

}
