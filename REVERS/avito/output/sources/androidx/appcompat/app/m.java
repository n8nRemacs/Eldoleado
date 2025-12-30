package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.C19929c;
import androidx.appcompat.app.p;
import androidx.appcompat.view.b;
import androidx.appcompat.widget.N0;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.Q;
import androidx.fragment.app.ActivityC22955m;
import androidx.view.C23485c;
import androidx.view.C23489g;
import androidx.view.V0;
import androidx.view.X0;
import com.avito.android.R;
import j.InterfaceC42153i;
import j.InterfaceC42159o;
import j.N;
import j.P;
import j.f0;

/* compiled from: AppCompatActivity.java */
/* loaded from: classes.dex */
public class m extends ActivityC22955m implements n, Q.a, C19929c.b {
    private p mDelegate;
    private Resources mResources;

    /* compiled from: AppCompatActivity.java */
    public class a implements C23485c.InterfaceC1922c {
        public a() {
        }

        @Override // androidx.view.C23485c.InterfaceC1922c
        @N
        public final Bundle H() {
            Bundle bundle = new Bundle();
            m.this.getDelegate().getClass();
            return bundle;
        }
    }

    /* compiled from: AppCompatActivity.java */
    public class b implements g.f {
        public b() {
        }

        @Override // g.f
        public final void a(@N androidx.view.n nVar) {
            m mVar = m.this;
            p delegate = mVar.getDelegate();
            delegate.n();
            mVar.getSavedStateRegistry().a("androidx:appcompat");
            delegate.r();
        }
    }

    public m() {
        initDelegate();
    }

    private void initDelegate() {
        getSavedStateRegistry().c("androidx:appcompat", new a());
        addOnContextAvailableListener(new b());
    }

    private void initViewTreeOwners() {
        V0.b(getWindow().getDecorView(), this);
        X0.b(getWindow().getDecorView(), this);
        C23489g.b(getWindow().getDecorView(), this);
        getWindow().getDecorView().setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, this);
    }

    private boolean performMenuItemShortcut(KeyEvent keyEvent) {
        return false;
    }

    @Override // androidx.view.n, android.app.Activity
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().c(view, layoutParams);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(getDelegate().f(context));
    }

    @Override // android.app.Activity
    public void closeOptionsMenu() {
        AbstractC19927a supportActionBar = getSupportActionBar();
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.f()) {
                super.closeOptionsMenu();
            }
        }
    }

    @Override // androidx.core.app.ActivityC22757o, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        AbstractC19927a supportActionBar = getSupportActionBar();
        if (keyCode == 82 && supportActionBar != null && supportActionBar.p(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.app.Activity
    public <T extends View> T findViewById(@j.D int i12) {
        return (T) getDelegate().h(i12);
    }

    @N
    public p getDelegate() {
        if (this.mDelegate == null) {
            p.d dVar = p.f21559b;
            this.mDelegate = new AppCompatDelegateImpl(this, null, this, this);
        }
        return this.mDelegate;
    }

    @Override // android.app.Activity
    @N
    public MenuInflater getMenuInflater() {
        return getDelegate().l();
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = this.mResources;
        if (resources == null) {
            int i12 = N0.f22223a;
        }
        return resources == null ? super.getResources() : resources;
    }

    @P
    public AbstractC19927a getSupportActionBar() {
        return getDelegate().m();
    }

    @Override // androidx.core.app.Q.a
    @P
    public Intent getSupportParentActivityIntent() {
        return androidx.core.app.v.b(this);
    }

    @Override // android.app.Activity
    public void invalidateOptionsMenu() {
        getDelegate().o();
    }

    @Override // androidx.view.n, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@N Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getDelegate().q(configuration);
        if (this.mResources != null) {
            this.mResources.updateConfiguration(super.getResources().getConfiguration(), super.getResources().getDisplayMetrics());
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onContentChanged() {
        onSupportContentChanged();
    }

    public void onCreateSupportNavigateUpTaskStack(@N Q q12) {
        q12.getClass();
        Intent supportParentActivityIntent = getSupportParentActivityIntent();
        if (supportParentActivityIntent == null) {
            supportParentActivityIntent = androidx.core.app.v.b(this);
        }
        if (supportParentActivityIntent != null) {
            ComponentName component = supportParentActivityIntent.getComponent();
            if (component == null) {
                component = supportParentActivityIntent.resolveActivity(q12.f44514c.getPackageManager());
            }
            q12.a(component);
            q12.f44513b.add(supportParentActivityIntent);
        }
    }

    @Override // androidx.fragment.app.ActivityC22955m, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getDelegate().s();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i12, KeyEvent keyEvent) {
        if (performMenuItemShortcut(keyEvent)) {
            return true;
        }
        return super.onKeyDown(i12, keyEvent);
    }

    @Override // androidx.fragment.app.ActivityC22955m, androidx.view.n, android.app.Activity, android.view.Window.Callback
    public final boolean onMenuItemSelected(int i12, @N MenuItem menuItem) {
        if (super.onMenuItemSelected(i12, menuItem)) {
            return true;
        }
        AbstractC19927a supportActionBar = getSupportActionBar();
        if (menuItem.getItemId() != 16908332 || supportActionBar == null || (supportActionBar.i() & 4) == 0) {
            return false;
        }
        return onSupportNavigateUp();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuOpened(int i12, Menu menu) {
        return super.onMenuOpened(i12, menu);
    }

    @Override // androidx.view.n, android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i12, @N Menu menu) {
        super.onPanelClosed(i12, menu);
    }

    @Override // android.app.Activity
    public void onPostCreate(@P Bundle bundle) {
        super.onPostCreate(bundle);
        getDelegate().t();
    }

    @Override // androidx.fragment.app.ActivityC22955m, android.app.Activity
    public void onPostResume() {
        super.onPostResume();
        getDelegate().u();
    }

    @Override // androidx.fragment.app.ActivityC22955m, android.app.Activity
    public void onStart() {
        super.onStart();
        getDelegate().v();
    }

    @Override // androidx.fragment.app.ActivityC22955m, android.app.Activity
    public void onStop() {
        super.onStop();
        getDelegate().w();
    }

    public boolean onSupportNavigateUp() {
        Intent supportParentActivityIntent = getSupportParentActivityIntent();
        if (supportParentActivityIntent == null) {
            return false;
        }
        if (!supportShouldUpRecreateTask(supportParentActivityIntent)) {
            supportNavigateUpTo(supportParentActivityIntent);
            return true;
        }
        Q q12 = new Q(this);
        onCreateSupportNavigateUpTaskStack(q12);
        onPrepareSupportNavigateUpTaskStack(q12);
        q12.b();
        try {
            finishAffinity();
            return true;
        } catch (IllegalStateException unused) {
            finish();
            return true;
        }
    }

    @Override // android.app.Activity
    public void onTitleChanged(CharSequence charSequence, int i12) {
        super.onTitleChanged(charSequence, i12);
        getDelegate().H(charSequence);
    }

    @Override // androidx.appcompat.app.n
    @P
    public androidx.appcompat.view.b onWindowStartingSupportActionMode(@N b.a aVar) {
        return null;
    }

    @Override // android.app.Activity
    public void openOptionsMenu() {
        AbstractC19927a supportActionBar = getSupportActionBar();
        if (getWindow().hasFeature(0)) {
            if (supportActionBar == null || !supportActionBar.q()) {
                super.openOptionsMenu();
            }
        }
    }

    @Override // androidx.view.n, android.app.Activity
    public void setContentView(@j.I int i12) {
        initViewTreeOwners();
        getDelegate().A(i12);
    }

    public void setSupportActionBar(@P Toolbar toolbar) {
        getDelegate().F(toolbar);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(@f0 int i12) {
        super.setTheme(i12);
        getDelegate().G(i12);
    }

    public void supportNavigateUpTo(@N Intent intent) {
        navigateUpTo(intent);
    }

    public boolean supportShouldUpRecreateTask(@N Intent intent) {
        return shouldUpRecreateTask(intent);
    }

    @InterfaceC42159o
    public m(@j.I int i12) {
        super(i12);
        initDelegate();
    }

    @Override // androidx.view.n, android.app.Activity
    public void setContentView(View view) {
        initViewTreeOwners();
        getDelegate().B(view);
    }

    @Override // androidx.view.n, android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        initViewTreeOwners();
        getDelegate().C(view, layoutParams);
    }

    @Deprecated
    public void onSupportContentChanged() {
    }

    public void onLocalesChanged(@N androidx.core.os.q qVar) {
    }

    public void onNightModeChanged(int i12) {
    }

    public void onPrepareSupportNavigateUpTaskStack(@N Q q12) {
    }

    @Override // androidx.appcompat.app.n
    @InterfaceC42153i
    public void onSupportActionModeFinished(@N androidx.appcompat.view.b bVar) {
    }

    @Override // androidx.appcompat.app.n
    @InterfaceC42153i
    public void onSupportActionModeStarted(@N androidx.appcompat.view.b bVar) {
    }
}
