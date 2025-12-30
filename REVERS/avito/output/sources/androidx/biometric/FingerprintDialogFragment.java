package androidx.biometric;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.l;
import androidx.biometric.p;
import androidx.fragment.app.ActivityC22955m;
import androidx.fragment.app.DialogFragment;
import androidx.view.C23038g0;
import androidx.view.P0;
import com.avito.android.R;
import j.N;
import j.P;
import j.X;

@RestrictTo
/* loaded from: classes.dex */
public class FingerprintDialogFragment extends DialogFragment {

    /* renamed from: f0, reason: collision with root package name */
    public final Handler f22736f0 = new Handler(Looper.getMainLooper());

    /* renamed from: g0, reason: collision with root package name */
    public final Runnable f22737g0 = new a();

    /* renamed from: h0, reason: collision with root package name */
    public r f22738h0;

    /* renamed from: i0, reason: collision with root package name */
    public int f22739i0;

    /* renamed from: j0, reason: collision with root package name */
    public int f22740j0;

    /* renamed from: k0, reason: collision with root package name */
    @P
    public ImageView f22741k0;

    /* renamed from: l0, reason: collision with root package name */
    @P
    public TextView f22742l0;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            FingerprintDialogFragment fingerprintDialogFragment = FingerprintDialogFragment.this;
            Context context = fingerprintDialogFragment.getContext();
            if (context == null) {
                return;
            }
            fingerprintDialogFragment.f22738h0.oe(1);
            fingerprintDialogFragment.f22738h0.ne(context.getString(R.string.fingerprint_dialog_touch_sensor));
        }
    }

    public class b implements DialogInterface.OnClickListener {
        public b() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i12) {
            FingerprintDialogFragment.this.f22738h0.pe(true);
        }
    }

    @X
    public static class c {
        private c() {
        }

        public static void a(@N Drawable drawable) {
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }
        }
    }

    @X
    public static class d {
        private d() {
        }

        public static int a() {
            return R.attr.colorError;
        }
    }

    public final int b5(int i12) throws Resources.NotFoundException {
        Context context = getContext();
        ActivityC22955m activityC22955mL1 = l1();
        if (context == null || activityC22955mL1 == null) {
            return 0;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i12, typedValue, true);
        TypedArray typedArrayObtainStyledAttributes = activityC22955mL1.obtainStyledAttributes(typedValue.data, new int[]{i12});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        return color;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public final void onCancel(@N DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        r rVar = this.f22738h0;
        if (rVar.f22808c0 == null) {
            rVar.f22808c0 = new C23038g0<>();
        }
        r.qe(rVar.f22808c0, Boolean.TRUE);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(@P Bundle bundle) {
        super.onCreate(bundle);
        ActivityC22955m activityC22955mL1 = l1();
        if (activityC22955mL1 != null) {
            r rVar = (r) new P0(activityC22955mL1).a(r.class);
            this.f22738h0 = rVar;
            if (rVar.f22810e0 == null) {
                rVar.f22810e0 = new C23038g0<>();
            }
            rVar.f22810e0.observe(this, new w(this));
            r rVar2 = this.f22738h0;
            if (rVar2.f22811f0 == null) {
                rVar2.f22811f0 = new C23038g0<>();
            }
            rVar2.f22811f0.observe(this, new x(this));
        }
        this.f22739i0 = b5(d.a());
        this.f22740j0 = b5(android.R.attr.textColorSecondary);
    }

    @Override // androidx.fragment.app.DialogFragment
    @N
    public final Dialog onCreateDialog(@P Bundle bundle) {
        l.a aVar = new l.a(requireContext());
        p.d dVar = this.f22738h0.f22790K;
        aVar.setTitle(dVar != null ? dVar.f22776a : null);
        View viewInflate = LayoutInflater.from(aVar.getContext()).inflate(R.layout.fingerprint_dialog_layout, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.fingerprint_subtitle);
        if (textView != null) {
            p.d dVar2 = this.f22738h0.f22790K;
            String str = dVar2 != null ? dVar2.f22777b : null;
            if (TextUtils.isEmpty(str)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
                textView.setText(str);
            }
        }
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.fingerprint_description);
        if (textView2 != null) {
            p.d dVar3 = this.f22738h0.f22790K;
            String str2 = dVar3 != null ? dVar3.f22778c : null;
            if (TextUtils.isEmpty(str2)) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
                textView2.setText(str2);
            }
        }
        this.f22741k0 = (ImageView) viewInflate.findViewById(R.id.fingerprint_icon);
        this.f22742l0 = (TextView) viewInflate.findViewById(R.id.fingerprint_error);
        aVar.setNegativeButton(androidx.biometric.c.b(this.f22738h0.ke()) ? getString(R.string.confirm_device_credential_password) : this.f22738h0.le(), new b());
        aVar.setView(viewInflate);
        androidx.appcompat.app.l lVarCreate = aVar.create();
        lVarCreate.setCanceledOnTouchOutside(false);
        return lVarCreate;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.f22736f0.removeCallbacksAndMessages(null);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        r rVar = this.f22738h0;
        rVar.f22809d0 = 0;
        rVar.oe(1);
        this.f22738h0.ne(getString(R.string.fingerprint_dialog_touch_sensor));
    }
}
