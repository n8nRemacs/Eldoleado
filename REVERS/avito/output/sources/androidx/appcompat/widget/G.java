package androidx.appcompat.widget;

import android.app.Activity;
import android.text.Selection;
import android.text.Spannable;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.C22814d;
import androidx.core.view.C22823h0;
import j.InterfaceC42164u;

/* compiled from: AppCompatReceiveContentHelper.java */
/* loaded from: classes.dex */
final class G {

    /* compiled from: AppCompatReceiveContentHelper.java */
    @j.X
    public static final class a {
        @InterfaceC42164u
        public static boolean a(@j.N DragEvent dragEvent, @j.N TextView textView, @j.N Activity activity) {
            activity.requestDragAndDropPermissions(dragEvent);
            int offsetForPosition = textView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
            textView.beginBatchEdit();
            try {
                Selection.setSelection((Spannable) textView.getText(), offsetForPosition);
                C22823h0.w(textView, new C22814d.b(dragEvent.getClipData(), 3).a());
                textView.endBatchEdit();
                return true;
            } catch (Throwable th2) {
                textView.endBatchEdit();
                throw th2;
            }
        }

        @InterfaceC42164u
        public static boolean b(@j.N DragEvent dragEvent, @j.N View view, @j.N Activity activity) {
            activity.requestDragAndDropPermissions(dragEvent);
            C22823h0.w(view, new C22814d.b(dragEvent.getClipData(), 3).a());
            return true;
        }
    }
}
