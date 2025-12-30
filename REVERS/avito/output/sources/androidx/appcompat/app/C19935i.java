package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

/* compiled from: AlertController.java */
/* renamed from: androidx.appcompat.app.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19935i extends CursorAdapter {
    @Override // android.widget.CursorAdapter
    public final void bindView(View view, Context context, Cursor cursor) {
        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(0));
        cursor.getPosition();
        cursor.getInt(0);
        throw null;
    }

    @Override // android.widget.CursorAdapter
    public final View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        throw null;
    }
}
