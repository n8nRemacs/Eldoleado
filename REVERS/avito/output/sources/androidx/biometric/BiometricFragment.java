package androidx.biometric;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.biometrics.BiometricPrompt$AuthenticationCallback;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.RestrictTo;
import androidx.biometric.b;
import androidx.biometric.p;
import androidx.biometric.r;
import androidx.biometric.s;
import androidx.biometric.y;
import androidx.core.hardware.fingerprint.b;
import androidx.core.os.C22778f;
import androidx.fragment.app.ActivityC22955m;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.H;
import androidx.view.C23038g0;
import androidx.view.P0;
import com.avito.android.R;
import j.N;
import j.P;
import j.X;
import j.k0;
import java.lang.ref.WeakReference;
import java.security.Signature;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@RestrictTo
/* loaded from: classes.dex */
public class BiometricFragment extends Fragment {

    /* renamed from: f0, reason: collision with root package name */
    @k0
    public final Handler f22727f0 = new Handler(Looper.getMainLooper());

    /* renamed from: g0, reason: collision with root package name */
    @k0
    public r f22728g0;

    public class a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f22729b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ CharSequence f22730c;

        public a(int i12, CharSequence charSequence) {
            this.f22729b = i12;
            this.f22730c = charSequence;
        }

        @Override // java.lang.Runnable
        public final void run() {
            r rVar = BiometricFragment.this.f22728g0;
            if (rVar.f22789J == null) {
                rVar.f22789J = new q();
            }
            rVar.f22789J.a(this.f22729b, this.f22730c);
        }
    }

    @X
    public static class b {
        private b() {
        }

        @P
        public static Intent a(@N KeyguardManager keyguardManager, @P CharSequence charSequence, @P CharSequence charSequence2) {
            return keyguardManager.createConfirmDeviceCredentialIntent(charSequence, charSequence2);
        }
    }

    @X
    public static class c {
        private c() {
        }

        public static void a(@N BiometricPrompt biometricPrompt, @N BiometricPrompt.CryptoObject cryptoObject, @N CancellationSignal cancellationSignal, @N Executor executor, @N BiometricPrompt$AuthenticationCallback biometricPrompt$AuthenticationCallback) {
            biometricPrompt.authenticate(cryptoObject, cancellationSignal, executor, biometricPrompt$AuthenticationCallback);
        }

        public static void b(@N BiometricPrompt biometricPrompt, @N CancellationSignal cancellationSignal, @N Executor executor, @N BiometricPrompt$AuthenticationCallback biometricPrompt$AuthenticationCallback) {
            biometricPrompt.authenticate(cancellationSignal, executor, biometricPrompt$AuthenticationCallback);
        }

        @N
        public static BiometricPrompt c(@N BiometricPrompt.Builder builder) {
            return builder.build();
        }

        @N
        public static BiometricPrompt.Builder d(@N Context context) {
            return new BiometricPrompt.Builder(context);
        }

        public static void e(@N BiometricPrompt.Builder builder, @N CharSequence charSequence) {
            builder.setDescription(charSequence);
        }

        public static void f(@N BiometricPrompt.Builder builder, @N CharSequence charSequence, @N Executor executor, @N DialogInterface.OnClickListener onClickListener) {
            builder.setNegativeButton(charSequence, executor, onClickListener);
        }

        public static void g(@N BiometricPrompt.Builder builder, @N CharSequence charSequence) {
            builder.setSubtitle(charSequence);
        }

        public static void h(@N BiometricPrompt.Builder builder, @N CharSequence charSequence) {
            builder.setTitle(charSequence);
        }
    }

    @X
    public static class d {
        private d() {
        }

        public static void a(@N BiometricPrompt.Builder builder, boolean z12) {
            builder.setConfirmationRequired(z12);
        }

        public static void b(@N BiometricPrompt.Builder builder, boolean z12) {
            builder.setDeviceCredentialAllowed(z12);
        }
    }

    @X
    public static class e {
        private e() {
        }

        public static void a(@N BiometricPrompt.Builder builder, int i12) {
            builder.setAllowedAuthenticators(i12);
        }
    }

    public static class f implements Executor {

        /* renamed from: b, reason: collision with root package name */
        public final Handler f22732b = new Handler(Looper.getMainLooper());

        @Override // java.util.concurrent.Executor
        public final void execute(@N Runnable runnable) {
            this.f22732b.post(runnable);
        }
    }

    public static class g implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final WeakReference<BiometricFragment> f22733b;

        public g(@P BiometricFragment biometricFragment) {
            this.f22733b = new WeakReference<>(biometricFragment);
        }

        @Override // java.lang.Runnable
        public final void run() throws Resources.NotFoundException {
            WeakReference<BiometricFragment> weakReference = this.f22733b;
            if (weakReference.get() != null) {
                weakReference.get().j5();
            }
        }
    }

    public static class h implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final WeakReference<r> f22734b;

        public h(@P r rVar) {
            this.f22734b = new WeakReference<>(rVar);
        }

        @Override // java.lang.Runnable
        public final void run() {
            WeakReference<r> weakReference = this.f22734b;
            if (weakReference.get() != null) {
                weakReference.get().f22800U = false;
            }
        }
    }

    public static class i implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        @N
        public final WeakReference<r> f22735b;

        public i(@P r rVar) {
            this.f22735b = new WeakReference<>(rVar);
        }

        @Override // java.lang.Runnable
        public final void run() {
            WeakReference<r> weakReference = this.f22735b;
            if (weakReference.get() != null) {
                weakReference.get().f22801V = false;
            }
        }
    }

    public final void b5(int i12) {
        if (i12 == 3 || !this.f22728g0.f22801V) {
            if (e5()) {
                this.f22728g0.f22796Q = i12;
                if (i12 == 1) {
                    h5(10, v.a(10, getContext()));
                }
            }
            r rVar = this.f22728g0;
            if (rVar.f22793N == null) {
                rVar.f22793N = new s();
            }
            s sVar = rVar.f22793N;
            CancellationSignal cancellationSignal = sVar.f22815a;
            if (cancellationSignal != null) {
                try {
                    s.b.a(cancellationSignal);
                } catch (NullPointerException unused) {
                }
                sVar.f22815a = null;
            }
            C22778f c22778f = sVar.f22816b;
            if (c22778f != null) {
                try {
                    c22778f.a();
                } catch (NullPointerException unused2) {
                }
                sVar.f22816b = null;
            }
        }
    }

    public final void c5() {
        this.f22728g0.f22797R = false;
        if (isAdded()) {
            FragmentManager parentFragmentManager = getParentFragmentManager();
            FingerprintDialogFragment fingerprintDialogFragment = (FingerprintDialogFragment) parentFragmentManager.H("androidx.biometric.FingerprintDialogFragment");
            if (fingerprintDialogFragment != null) {
                if (fingerprintDialogFragment.isAdded()) {
                    fingerprintDialogFragment.dismissAllowingStateLoss();
                    return;
                }
                H hE2 = parentFragmentManager.e();
                hE2.m(fingerprintDialogFragment);
                hE2.f();
            }
        }
    }

    public final boolean d5() {
        return Build.VERSION.SDK_INT <= 28 && androidx.biometric.c.b(this.f22728g0.ke());
    }

    public final void dismiss() throws Resources.NotFoundException {
        this.f22728g0.f22797R = false;
        c5();
        if (!this.f22728g0.f22799T && isAdded()) {
            H hE2 = getParentFragmentManager().e();
            hE2.m(this);
            hE2.f();
        }
        Context context = getContext();
        if (context != null) {
            String str = Build.MODEL;
            if (Build.VERSION.SDK_INT == 29 && str != null) {
                for (String str2 : context.getResources().getStringArray(R.array.delay_showing_prompt_models)) {
                    if (str.equals(str2)) {
                        r rVar = this.f22728g0;
                        rVar.f22800U = true;
                        this.f22727f0.postDelayed(new h(rVar), 600L);
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean e5() throws android.content.res.Resources.NotFoundException {
        /*
            r9 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 28
            if (r0 < r1) goto L71
            androidx.fragment.app.m r2 = r9.l1()
            r3 = 0
            if (r2 == 0) goto L56
            androidx.biometric.r r4 = r9.f22728g0
            androidx.biometric.p$c r4 = r4.f22791L
            if (r4 == 0) goto L56
            java.lang.String r4 = android.os.Build.MANUFACTURER
            java.lang.String r5 = android.os.Build.MODEL
            if (r0 == r1) goto L1a
            goto L56
        L1a:
            if (r4 != 0) goto L1d
            goto L38
        L1d:
            android.content.res.Resources r0 = r2.getResources()
            r6 = 2130903052(0x7f03000c, float:1.7412911E38)
            java.lang.String[] r0 = r0.getStringArray(r6)
            int r6 = r0.length
            r7 = r3
        L2a:
            if (r7 >= r6) goto L38
            r8 = r0[r7]
            boolean r8 = r4.equalsIgnoreCase(r8)
            if (r8 == 0) goto L35
            goto L71
        L35:
            int r7 = r7 + 1
            goto L2a
        L38:
            if (r5 != 0) goto L3b
            goto L56
        L3b:
            android.content.res.Resources r0 = r2.getResources()
            r2 = 2130903051(0x7f03000b, float:1.741291E38)
            java.lang.String[] r0 = r0.getStringArray(r2)
            int r2 = r0.length
            r4 = r3
        L48:
            if (r4 >= r2) goto L56
            r6 = r0[r4]
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L53
            goto L71
        L53:
            int r4 = r4 + 1
            goto L48
        L56:
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 != r1) goto L72
            android.content.Context r0 = r9.getContext()
            if (r0 == 0) goto L71
            android.content.pm.PackageManager r1 = r0.getPackageManager()
            if (r1 == 0) goto L71
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            boolean r0 = androidx.biometric.z.a.a(r0)
            if (r0 == 0) goto L71
            goto L72
        L71:
            r3 = 1
        L72:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.biometric.BiometricFragment.e5():boolean");
    }

    @X
    public final void f5() throws Resources.NotFoundException {
        ActivityC22955m activityC22955mL1 = l1();
        if (activityC22955mL1 == null) {
            return;
        }
        KeyguardManager keyguardManagerA = y.b.a(activityC22955mL1);
        if (keyguardManagerA == null) {
            g5(12, getString(R.string.generic_error_no_keyguard));
            return;
        }
        p.d dVar = this.f22728g0.f22790K;
        String str = dVar != null ? dVar.f22776a : null;
        String str2 = dVar != null ? dVar.f22777b : null;
        String str3 = dVar != null ? dVar.f22778c : null;
        if (str2 == null) {
            str2 = str3;
        }
        Intent intentA = b.a(keyguardManagerA, str, str2);
        if (intentA == null) {
            g5(14, getString(R.string.generic_error_no_device_credential));
            return;
        }
        this.f22728g0.f22799T = true;
        if (e5()) {
            c5();
        }
        intentA.setFlags(134742016);
        startActivityForResult(intentA, 1);
    }

    public final void g5(int i12, @N CharSequence charSequence) throws Resources.NotFoundException {
        h5(i12, charSequence);
        dismiss();
    }

    public final void h5(int i12, @N CharSequence charSequence) {
        r rVar = this.f22728g0;
        if (!rVar.f22799T && rVar.f22798S) {
            rVar.f22798S = false;
            Executor bVar = rVar.f22788E;
            if (bVar == null) {
                bVar = new r.b();
            }
            bVar.execute(new a(i12, charSequence));
        }
    }

    public final void i5(@P CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = getString(R.string.default_error_msg);
        }
        this.f22728g0.oe(2);
        this.f22728g0.ne(charSequence);
    }

    public final void j5() throws Resources.NotFoundException {
        int i12;
        if (this.f22728g0.f22797R || getContext() == null) {
            return;
        }
        r rVar = this.f22728g0;
        rVar.f22797R = true;
        rVar.f22798S = true;
        dVar = null;
        b.d dVar = null;
        if (!e5()) {
            BiometricPrompt.Builder builderD = c.d(requireContext().getApplicationContext());
            p.d dVar2 = this.f22728g0.f22790K;
            String str = dVar2 != null ? dVar2.f22776a : null;
            String str2 = dVar2 != null ? dVar2.f22777b : null;
            String str3 = dVar2 != null ? dVar2.f22778c : null;
            if (str != null) {
                c.h(builderD, str);
            }
            if (str2 != null) {
                c.g(builderD, str2);
            }
            if (str3 != null) {
                c.e(builderD, str3);
            }
            CharSequence charSequenceLe = this.f22728g0.le();
            if (!TextUtils.isEmpty(charSequenceLe)) {
                Executor bVar = this.f22728g0.f22788E;
                if (bVar == null) {
                    bVar = new r.b();
                }
                r rVar2 = this.f22728g0;
                if (rVar2.f22794O == null) {
                    rVar2.f22794O = new r.c(rVar2);
                }
                c.f(builderD, charSequenceLe, bVar, rVar2.f22794O);
            }
            int i13 = Build.VERSION.SDK_INT;
            if (i13 >= 29) {
                p.d dVar3 = this.f22728g0.f22790K;
                d.a(builderD, dVar3 == null || dVar3.f22780e);
            }
            int iKe = this.f22728g0.ke();
            if (i13 >= 30) {
                e.a(builderD, iKe);
            } else if (i13 >= 29) {
                d.b(builderD, androidx.biometric.c.b(iKe));
            }
            BiometricPrompt biometricPromptC = c.c(builderD);
            Context context = getContext();
            BiometricPrompt.CryptoObject cryptoObjectA = t.a(this.f22728g0.f22791L);
            r rVar3 = this.f22728g0;
            if (rVar3.f22793N == null) {
                rVar3.f22793N = new s();
            }
            s sVar = rVar3.f22793N;
            if (sVar.f22815a == null) {
                sVar.f22815a = s.b.b();
            }
            CancellationSignal cancellationSignal = sVar.f22815a;
            f fVar = new f();
            r rVar4 = this.f22728g0;
            if (rVar4.f22792M == null) {
                rVar4.f22792M = new androidx.biometric.b(new r.a(rVar4));
            }
            androidx.biometric.b bVar2 = rVar4.f22792M;
            if (bVar2.f22746a == null) {
                bVar2.f22746a = b.a.a(bVar2.f22748c);
            }
            BiometricPrompt$AuthenticationCallback biometricPrompt$AuthenticationCallback = bVar2.f22746a;
            try {
                if (cryptoObjectA == null) {
                    c.b(biometricPromptC, cancellationSignal, fVar, biometricPrompt$AuthenticationCallback);
                } else {
                    c.a(biometricPromptC, cryptoObjectA, cancellationSignal, fVar, biometricPrompt$AuthenticationCallback);
                }
                return;
            } catch (NullPointerException unused) {
                g5(1, context != null ? context.getString(R.string.default_error_msg) : "");
                return;
            }
        }
        Context applicationContext = requireContext().getApplicationContext();
        androidx.core.hardware.fingerprint.b bVar3 = new androidx.core.hardware.fingerprint.b(applicationContext);
        FingerprintManager fingerprintManagerB = androidx.core.hardware.fingerprint.b.b(applicationContext);
        if (fingerprintManagerB == null || !fingerprintManagerB.isHardwareDetected()) {
            i12 = 12;
        } else {
            FingerprintManager fingerprintManagerB2 = androidx.core.hardware.fingerprint.b.b(applicationContext);
            i12 = (fingerprintManagerB2 == null || !fingerprintManagerB2.hasEnrolledFingerprints()) ? 11 : 0;
        }
        if (i12 != 0) {
            g5(i12, v.a(i12, applicationContext));
            return;
        }
        if (isAdded()) {
            this.f22728g0.f22807b0 = true;
            String str4 = Build.MODEL;
            if (Build.VERSION.SDK_INT == 28 && str4 != null) {
                for (String str5 : applicationContext.getResources().getStringArray(R.array.hide_fingerprint_instantly_prefixes)) {
                    if (str4.startsWith(str5)) {
                        break;
                    }
                }
                this.f22727f0.postDelayed(new l(this), 500L);
                new FingerprintDialogFragment().show(getParentFragmentManager(), "androidx.biometric.FingerprintDialogFragment");
            } else {
                this.f22727f0.postDelayed(new l(this), 500L);
                new FingerprintDialogFragment().show(getParentFragmentManager(), "androidx.biometric.FingerprintDialogFragment");
            }
            r rVar5 = this.f22728g0;
            rVar5.f22796Q = 0;
            p.c cVar = rVar5.f22791L;
            if (cVar != null) {
                Cipher cipher = cVar.f22773b;
                if (cipher != null) {
                    dVar = new b.d(cipher);
                } else {
                    Signature signature = cVar.f22772a;
                    if (signature != null) {
                        dVar = new b.d(signature);
                    } else {
                        Mac mac = cVar.f22774c;
                        if (mac != null) {
                            dVar = new b.d(mac);
                        }
                    }
                }
            }
            if (rVar5.f22793N == null) {
                rVar5.f22793N = new s();
            }
            s sVar2 = rVar5.f22793N;
            if (sVar2.f22816b == null) {
                sVar2.f22816b = new C22778f();
            }
            C22778f c22778f = sVar2.f22816b;
            r rVar6 = this.f22728g0;
            if (rVar6.f22792M == null) {
                rVar6.f22792M = new androidx.biometric.b(new r.a(rVar6));
            }
            androidx.biometric.b bVar4 = rVar6.f22792M;
            if (bVar4.f22747b == null) {
                bVar4.f22747b = new C19978a(bVar4);
            }
            try {
                bVar3.a(dVar, c22778f, bVar4.f22747b);
            } catch (NullPointerException unused2) {
                g5(1, v.a(1, applicationContext));
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i12, int i13, @P Intent intent) throws Resources.NotFoundException {
        super.onActivityResult(i12, i13, intent);
        if (i12 == 1) {
            r rVar = this.f22728g0;
            rVar.f22799T = false;
            if (i13 != -1) {
                g5(10, getString(R.string.generic_error_user_canceled));
                return;
            }
            p.b bVar = new p.b(null, 1);
            if (rVar.f22798S) {
                rVar.f22798S = false;
                Executor bVar2 = rVar.f22788E;
                if (bVar2 == null) {
                    bVar2 = new r.b();
                }
                bVar2.execute(new n(this, bVar));
            }
            dismiss();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(@P Bundle bundle) {
        super.onCreate(bundle);
        if (l1() == null) {
            return;
        }
        r rVar = (r) new P0(l1()).a(r.class);
        this.f22728g0 = rVar;
        if (rVar.f22802W == null) {
            rVar.f22802W = new C23038g0<>();
        }
        rVar.f22802W.observe(this, new androidx.biometric.f(this));
        r rVar2 = this.f22728g0;
        if (rVar2.f22803X == null) {
            rVar2.f22803X = new C23038g0<>();
        }
        rVar2.f22803X.observe(this, new androidx.biometric.g(this));
        r rVar3 = this.f22728g0;
        if (rVar3.f22804Y == null) {
            rVar3.f22804Y = new C23038g0<>();
        }
        rVar3.f22804Y.observe(this, new androidx.biometric.h(this));
        r rVar4 = this.f22728g0;
        if (rVar4.f22805Z == null) {
            rVar4.f22805Z = new C23038g0<>();
        }
        rVar4.f22805Z.observe(this, new androidx.biometric.i(this));
        r rVar5 = this.f22728g0;
        if (rVar5.f22806a0 == null) {
            rVar5.f22806a0 = new C23038g0<>();
        }
        rVar5.f22806a0.observe(this, new j(this));
        r rVar6 = this.f22728g0;
        if (rVar6.f22808c0 == null) {
            rVar6.f22808c0 = new C23038g0<>();
        }
        rVar6.f22808c0.observe(this, new k(this));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT == 29 && androidx.biometric.c.b(this.f22728g0.ke())) {
            r rVar = this.f22728g0;
            rVar.f22801V = true;
            this.f22727f0.postDelayed(new i(rVar), 250L);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 29 || this.f22728g0.f22799T) {
            return;
        }
        ActivityC22955m activityC22955mL1 = l1();
        if (activityC22955mL1 == null || !activityC22955mL1.isChangingConfigurations()) {
            b5(0);
        }
    }
}
