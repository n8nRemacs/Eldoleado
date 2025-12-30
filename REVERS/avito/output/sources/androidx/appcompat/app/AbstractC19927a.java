package androidx.appcompat.app;

import android.content.Context;
import android.view.KeyEvent;
import android.view.ViewGroup;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.b;
import j.InterfaceC42165v;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: ActionBar.java */
/* renamed from: androidx.appcompat.app.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC19927a {

    /* compiled from: ActionBar.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.appcompat.app.a$a, reason: collision with other inner class name */
    public @interface InterfaceC1514a {
    }

    /* compiled from: ActionBar.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.appcompat.app.a$c */
    public @interface c {
    }

    /* compiled from: ActionBar.java */
    /* renamed from: androidx.appcompat.app.a$d */
    public interface d {
        void a();
    }

    /* compiled from: ActionBar.java */
    @Deprecated
    /* renamed from: androidx.appcompat.app.a$e */
    public interface e {
    }

    /* compiled from: ActionBar.java */
    @Deprecated
    /* renamed from: androidx.appcompat.app.a$f */
    public static abstract class f {
    }

    /* compiled from: ActionBar.java */
    @Deprecated
    /* renamed from: androidx.appcompat.app.a$g */
    public interface g {
    }

    @RestrictTo
    public boolean f() {
        return false;
    }

    @RestrictTo
    public boolean g() {
        return false;
    }

    public abstract int i();

    public Context j() {
        return null;
    }

    public abstract void k();

    @RestrictTo
    public boolean l() {
        return false;
    }

    @RestrictTo
    public boolean o(int i12, KeyEvent keyEvent) {
        return false;
    }

    @RestrictTo
    public boolean p(KeyEvent keyEvent) {
        return false;
    }

    @RestrictTo
    public boolean q() {
        return false;
    }

    public abstract void s(boolean z12);

    public abstract void t();

    public abstract void w(CharSequence charSequence);

    @RestrictTo
    public androidx.appcompat.view.b y(b.a aVar) {
        return null;
    }

    /* compiled from: ActionBar.java */
    /* renamed from: androidx.appcompat.app.a$b */
    public static class b extends ViewGroup.MarginLayoutParams {

        /* renamed from: a, reason: collision with root package name */
        public int f21545a;

        public b(b bVar) {
            super((ViewGroup.MarginLayoutParams) bVar);
            this.f21545a = 0;
            this.f21545a = bVar.f21545a;
        }

        public b(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f21545a = 0;
        }
    }

    @RestrictTo
    public void m() {
    }

    public void n() {
    }

    @RestrictTo
    public void h(boolean z12) {
    }

    @RestrictTo
    public void r(boolean z12) {
    }

    public void u(@InterfaceC42165v int i12) {
    }

    @RestrictTo
    public void v(boolean z12) {
    }

    @RestrictTo
    public void x(CharSequence charSequence) {
    }
}
