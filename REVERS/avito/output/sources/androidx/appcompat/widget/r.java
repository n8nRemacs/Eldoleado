package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.C19962m0;
import androidx.core.graphics.C22767g;
import com.avito.android.R;
import j.InterfaceC42156l;
import j.InterfaceC42161q;
import j.InterfaceC42165v;

/* compiled from: AppCompatDrawableManager.java */
@RestrictTo
/* loaded from: classes.dex */
public final class r {

    /* renamed from: b, reason: collision with root package name */
    public static final PorterDuff.Mode f22609b = PorterDuff.Mode.SRC_IN;

    /* renamed from: c, reason: collision with root package name */
    public static r f22610c;

    /* renamed from: a, reason: collision with root package name */
    public C19962m0 f22611a;

    /* compiled from: AppCompatDrawableManager.java */
    public class a implements C19962m0.f {

        /* renamed from: a, reason: collision with root package name */
        public final int[] f22612a = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};

        /* renamed from: b, reason: collision with root package name */
        public final int[] f22613b = {R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};

        /* renamed from: c, reason: collision with root package name */
        public final int[] f22614c = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl, R.drawable.abc_text_select_handle_middle_mtrl, R.drawable.abc_text_select_handle_right_mtrl};

        /* renamed from: d, reason: collision with root package name */
        public final int[] f22615d = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};

        /* renamed from: e, reason: collision with root package name */
        public final int[] f22616e = {R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};

        /* renamed from: f, reason: collision with root package name */
        public final int[] f22617f = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material, R.drawable.abc_btn_check_material_anim, R.drawable.abc_btn_radio_material_anim};

        public static boolean a(int i12, int[] iArr) {
            for (int i13 : iArr) {
                if (i13 == i12) {
                    return true;
                }
            }
            return false;
        }

        public static ColorStateList b(@InterfaceC42156l int i12, @j.N Context context) {
            int iC2 = y0.c(R.attr.colorControlHighlight, context);
            int iB2 = y0.b(R.attr.colorButtonNormal, context);
            int[] iArr = y0.f22681b;
            int[] iArr2 = y0.f22683d;
            int iG2 = C22767g.g(iC2, i12);
            return new ColorStateList(new int[][]{iArr, iArr2, y0.f22682c, y0.f22685f}, new int[]{iB2, iG2, C22767g.g(iC2, i12), i12});
        }

        public static LayerDrawable c(@j.N C19962m0 c19962m0, @j.N Context context, @InterfaceC42161q int i12) throws Resources.NotFoundException {
            BitmapDrawable bitmapDrawable;
            BitmapDrawable bitmapDrawable2;
            BitmapDrawable bitmapDrawable3;
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(i12);
            Drawable drawableD = c19962m0.d(context, R.drawable.abc_star_black_48dp);
            Drawable drawableD2 = c19962m0.d(context, R.drawable.abc_star_half_black_48dp);
            if ((drawableD instanceof BitmapDrawable) && drawableD.getIntrinsicWidth() == dimensionPixelSize && drawableD.getIntrinsicHeight() == dimensionPixelSize) {
                bitmapDrawable = (BitmapDrawable) drawableD;
                bitmapDrawable2 = new BitmapDrawable(bitmapDrawable.getBitmap());
            } else {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawableD.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                drawableD.draw(canvas);
                bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
                bitmapDrawable2 = new BitmapDrawable(bitmapCreateBitmap);
            }
            bitmapDrawable2.setTileModeX(Shader.TileMode.REPEAT);
            if ((drawableD2 instanceof BitmapDrawable) && drawableD2.getIntrinsicWidth() == dimensionPixelSize && drawableD2.getIntrinsicHeight() == dimensionPixelSize) {
                bitmapDrawable3 = (BitmapDrawable) drawableD2;
            } else {
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                drawableD2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
                drawableD2.draw(canvas2);
                bitmapDrawable3 = new BitmapDrawable(bitmapCreateBitmap2);
            }
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable, bitmapDrawable3, bitmapDrawable2});
            layerDrawable.setId(0, android.R.id.background);
            layerDrawable.setId(1, android.R.id.secondaryProgress);
            layerDrawable.setId(2, android.R.id.progress);
            return layerDrawable;
        }

        public static void e(Drawable drawable, int i12, PorterDuff.Mode mode) {
            Drawable drawableMutate = drawable.mutate();
            if (mode == null) {
                mode = r.f22609b;
            }
            drawableMutate.setColorFilter(r.c(i12, mode));
        }

        public final ColorStateList d(int i12, @j.N Context context) {
            if (i12 == R.drawable.abc_edit_text_material) {
                return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_edittext);
            }
            if (i12 == R.drawable.abc_switch_track_mtrl_alpha) {
                return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_switch_track);
            }
            if (i12 != R.drawable.abc_switch_thumb_material) {
                if (i12 == R.drawable.abc_btn_default_mtrl_shape) {
                    return b(y0.c(R.attr.colorButtonNormal, context), context);
                }
                if (i12 == R.drawable.abc_btn_borderless_material) {
                    return b(0, context);
                }
                if (i12 == R.drawable.abc_btn_colored_material) {
                    return b(y0.c(R.attr.colorAccent, context), context);
                }
                if (i12 == R.drawable.abc_spinner_mtrl_am_alpha || i12 == R.drawable.abc_spinner_textfield_background_material) {
                    return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_spinner);
                }
                if (a(i12, this.f22613b)) {
                    return y0.d(R.attr.colorControlNormal, context);
                }
                if (a(i12, this.f22616e)) {
                    return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_default);
                }
                if (a(i12, this.f22617f)) {
                    return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_btn_checkable);
                }
                if (i12 == R.drawable.abc_seekbar_thumb_material) {
                    return androidx.core.content.d.getColorStateList(context, R.color.abc_tint_seek_thumb);
                }
                return null;
            }
            int[][] iArr = new int[3][];
            int[] iArr2 = new int[3];
            ColorStateList colorStateListD = y0.d(R.attr.colorSwitchThumbNormal, context);
            if (colorStateListD == null || !colorStateListD.isStateful()) {
                iArr[0] = y0.f22681b;
                iArr2[0] = y0.b(R.attr.colorSwitchThumbNormal, context);
                iArr[1] = y0.f22684e;
                iArr2[1] = y0.c(R.attr.colorControlActivated, context);
                iArr[2] = y0.f22685f;
                iArr2[2] = y0.c(R.attr.colorSwitchThumbNormal, context);
            } else {
                int[] iArr3 = y0.f22681b;
                iArr[0] = iArr3;
                iArr2[0] = colorStateListD.getColorForState(iArr3, 0);
                iArr[1] = y0.f22684e;
                iArr2[1] = y0.c(R.attr.colorControlActivated, context);
                iArr[2] = y0.f22685f;
                iArr2[2] = colorStateListD.getDefaultColor();
            }
            return new ColorStateList(iArr, iArr2);
        }
    }

    public static synchronized r a() {
        try {
            if (f22610c == null) {
                d();
            }
        } catch (Throwable th2) {
            throw th2;
        }
        return f22610c;
    }

    public static synchronized PorterDuffColorFilter c(int i12, PorterDuff.Mode mode) {
        return C19962m0.f(i12, mode);
    }

    public static synchronized void d() {
        if (f22610c == null) {
            r rVar = new r();
            f22610c = rVar;
            rVar.f22611a = C19962m0.b();
            C19962m0 c19962m0 = f22610c.f22611a;
            a aVar = new a();
            synchronized (c19962m0) {
                c19962m0.f22574e = aVar;
            }
        }
    }

    public static void e(Drawable drawable, B0 b02, int[] iArr) {
        PorterDuff.Mode mode = C19962m0.f22567f;
        int[] state = drawable.getState();
        if (drawable.mutate() == drawable) {
            if ((drawable instanceof LayerDrawable) && drawable.isStateful()) {
                drawable.setState(new int[0]);
                drawable.setState(state);
            }
            boolean z12 = b02.f22098d;
            if (!z12 && !b02.f22097c) {
                drawable.clearColorFilter();
                return;
            }
            PorterDuffColorFilter porterDuffColorFilterF = null;
            ColorStateList colorStateList = z12 ? b02.f22095a : null;
            PorterDuff.Mode mode2 = b02.f22097c ? b02.f22096b : C19962m0.f22567f;
            if (colorStateList != null && mode2 != null) {
                porterDuffColorFilterF = C19962m0.f(colorStateList.getColorForState(iArr, 0), mode2);
            }
            drawable.setColorFilter(porterDuffColorFilterF);
        }
    }

    public final synchronized Drawable b(@j.N Context context, @InterfaceC42165v int i12) {
        return this.f22611a.d(context, i12);
    }
}
