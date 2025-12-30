package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import com.avito.android.R;
import j.P;
import java.lang.ref.WeakReference;
import l.C43521a;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AlertController {

    /* renamed from: A, reason: collision with root package name */
    public final int f21316A;

    /* renamed from: B, reason: collision with root package name */
    public final int f21317B;

    /* renamed from: C, reason: collision with root package name */
    public final int f21318C;

    /* renamed from: D, reason: collision with root package name */
    public final int f21319D;

    /* renamed from: E, reason: collision with root package name */
    public final int f21320E;

    /* renamed from: F, reason: collision with root package name */
    public final boolean f21321F;

    /* renamed from: G, reason: collision with root package name */
    public final Handler f21322G;

    /* renamed from: a, reason: collision with root package name */
    public final Context f21324a;

    /* renamed from: b, reason: collision with root package name */
    public final l f21325b;

    /* renamed from: c, reason: collision with root package name */
    public final Window f21326c;

    /* renamed from: d, reason: collision with root package name */
    public CharSequence f21327d;

    /* renamed from: e, reason: collision with root package name */
    public CharSequence f21328e;

    /* renamed from: f, reason: collision with root package name */
    public RecycleListView f21329f;

    /* renamed from: g, reason: collision with root package name */
    public View f21330g;

    /* renamed from: h, reason: collision with root package name */
    public int f21331h;

    /* renamed from: j, reason: collision with root package name */
    public Button f21333j;

    /* renamed from: k, reason: collision with root package name */
    public CharSequence f21334k;

    /* renamed from: l, reason: collision with root package name */
    public Message f21335l;

    /* renamed from: m, reason: collision with root package name */
    public Button f21336m;

    /* renamed from: n, reason: collision with root package name */
    public CharSequence f21337n;

    /* renamed from: o, reason: collision with root package name */
    public Message f21338o;

    /* renamed from: p, reason: collision with root package name */
    public Button f21339p;

    /* renamed from: q, reason: collision with root package name */
    public CharSequence f21340q;

    /* renamed from: r, reason: collision with root package name */
    public Message f21341r;

    /* renamed from: s, reason: collision with root package name */
    public NestedScrollView f21342s;

    /* renamed from: t, reason: collision with root package name */
    public Drawable f21343t;

    /* renamed from: u, reason: collision with root package name */
    public ImageView f21344u;

    /* renamed from: v, reason: collision with root package name */
    public TextView f21345v;

    /* renamed from: w, reason: collision with root package name */
    public TextView f21346w;

    /* renamed from: x, reason: collision with root package name */
    public View f21347x;

    /* renamed from: y, reason: collision with root package name */
    public ListAdapter f21348y;

    /* renamed from: i, reason: collision with root package name */
    public boolean f21332i = false;

    /* renamed from: z, reason: collision with root package name */
    public int f21349z = -1;

    /* renamed from: H, reason: collision with root package name */
    public final View.OnClickListener f21323H = new a();

    public static class RecycleListView extends ListView {

        /* renamed from: b, reason: collision with root package name */
        public final int f21350b;

        /* renamed from: c, reason: collision with root package name */
        public final int f21351c;

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C43521a.m.f413371t);
            this.f21351c = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, -1);
            this.f21350b = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, -1);
        }
    }

    public class a implements View.OnClickListener {
        public a() {
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            Message message;
            Message message2;
            Message message3;
            AlertController alertController = AlertController.this;
            Message messageObtain = (view != alertController.f21333j || (message3 = alertController.f21335l) == null) ? (view != alertController.f21336m || (message2 = alertController.f21338o) == null) ? (view != alertController.f21339p || (message = alertController.f21341r) == null) ? null : Message.obtain(message) : Message.obtain(message2) : Message.obtain(message3);
            if (messageObtain != null) {
                messageObtain.sendToTarget();
            }
            alertController.f21322G.obtainMessage(1, alertController.f21325b).sendToTarget();
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public final ContextThemeWrapper f21353a;

        /* renamed from: b, reason: collision with root package name */
        public final LayoutInflater f21354b;

        /* renamed from: c, reason: collision with root package name */
        public Drawable f21355c;

        /* renamed from: d, reason: collision with root package name */
        public CharSequence f21356d;

        /* renamed from: e, reason: collision with root package name */
        public View f21357e;

        /* renamed from: f, reason: collision with root package name */
        public CharSequence f21358f;

        /* renamed from: g, reason: collision with root package name */
        public CharSequence f21359g;

        /* renamed from: h, reason: collision with root package name */
        public DialogInterface.OnClickListener f21360h;

        /* renamed from: i, reason: collision with root package name */
        public CharSequence f21361i;

        /* renamed from: j, reason: collision with root package name */
        public DialogInterface.OnClickListener f21362j;

        /* renamed from: k, reason: collision with root package name */
        public CharSequence f21363k;

        /* renamed from: l, reason: collision with root package name */
        public DialogInterface.OnClickListener f21364l;

        /* renamed from: n, reason: collision with root package name */
        public DialogInterface.OnCancelListener f21366n;

        /* renamed from: o, reason: collision with root package name */
        public DialogInterface.OnDismissListener f21367o;

        /* renamed from: p, reason: collision with root package name */
        public DialogInterface.OnKeyListener f21368p;

        /* renamed from: q, reason: collision with root package name */
        public CharSequence[] f21369q;

        /* renamed from: r, reason: collision with root package name */
        public ListAdapter f21370r;

        /* renamed from: s, reason: collision with root package name */
        public DialogInterface.OnClickListener f21371s;

        /* renamed from: t, reason: collision with root package name */
        public int f21372t;

        /* renamed from: u, reason: collision with root package name */
        public View f21373u;

        /* renamed from: v, reason: collision with root package name */
        public boolean[] f21374v;

        /* renamed from: w, reason: collision with root package name */
        public boolean f21375w;

        /* renamed from: x, reason: collision with root package name */
        public boolean f21376x;

        /* renamed from: z, reason: collision with root package name */
        public DialogInterface.OnMultiChoiceClickListener f21378z;

        /* renamed from: y, reason: collision with root package name */
        public int f21377y = -1;

        /* renamed from: m, reason: collision with root package name */
        public boolean f21365m = true;

        public interface a {
        }

        public b(ContextThemeWrapper contextThemeWrapper) {
            this.f21353a = contextThemeWrapper;
            this.f21354b = (LayoutInflater) contextThemeWrapper.getSystemService("layout_inflater");
        }
    }

    public static final class c extends Handler {

        /* renamed from: a, reason: collision with root package name */
        public WeakReference<DialogInterface> f21379a;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i12 = message.what;
            if (i12 == -3 || i12 == -2 || i12 == -1) {
                ((DialogInterface.OnClickListener) message.obj).onClick(this.f21379a.get(), message.what);
            } else {
                if (i12 != 1) {
                    return;
                }
                ((DialogInterface) message.obj).dismiss();
            }
        }
    }

    public static class d extends ArrayAdapter<CharSequence> {
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final long getItemId(int i12) {
            return i12;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return true;
        }
    }

    public AlertController(Context context, l lVar, Window window) {
        this.f21324a = context;
        this.f21325b = lVar;
        this.f21326c = window;
        c cVar = new c();
        cVar.f21379a = new WeakReference<>(lVar);
        this.f21322G = cVar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, C43521a.m.f413356e, R.attr.alertDialogStyle, 0);
        this.f21316A = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.getResourceId(2, 0);
        this.f21317B = typedArrayObtainStyledAttributes.getResourceId(4, 0);
        this.f21318C = typedArrayObtainStyledAttributes.getResourceId(5, 0);
        this.f21319D = typedArrayObtainStyledAttributes.getResourceId(7, 0);
        this.f21320E = typedArrayObtainStyledAttributes.getResourceId(3, 0);
        this.f21321F = typedArrayObtainStyledAttributes.getBoolean(6, true);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(1, 0);
        typedArrayObtainStyledAttributes.recycle();
        lVar.q();
    }

    public static boolean a(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (a(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    @P
    public static ViewGroup b(@P View view, @P View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    public final void c(int i12, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        Message messageObtainMessage = onClickListener != null ? this.f21322G.obtainMessage(i12, onClickListener) : null;
        if (i12 == -3) {
            this.f21340q = charSequence;
            this.f21341r = messageObtainMessage;
        } else if (i12 == -2) {
            this.f21337n = charSequence;
            this.f21338o = messageObtainMessage;
        } else {
            if (i12 != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.f21334k = charSequence;
            this.f21335l = messageObtainMessage;
        }
    }
}
