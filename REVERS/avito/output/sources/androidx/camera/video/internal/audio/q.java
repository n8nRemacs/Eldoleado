package androidx.camera.video.internal.audio;

import android.view.View;
import android.widget.HorizontalScrollView;
import androidx.camera.core.C20140q0;
import androidx.core.content.res.i;
import androidx.core.view.P;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.avito.android.avl_entry.impl.ui.external_item.ShortVideosViewImpl;
import com.avito.android.avl_entry.impl.ui.internal_item.show_all.a;
import com.avito.android.blueprint.input.v;
import com.avito.android.blueprints.input.B;
import com.avito.android.extended_profile.A;
import com.avito.android.extended_profile_ui_components.search_input.ProfileSearchInputView;
import com.avito.android.lib.design.input.Input;
import com.avito.android.lib.design.input.InputField;
import com.avito.android.lib.design.tab_layout.c;
import com.avito.android.photo_gallery.grid_gallery.w;
import com.avito.android.photo_gallery.ui.PhotoGallery;
import com.avito.android.photo_gallery_carousel.gallery_view.CarouselPhotoGalleryView;
import com.avito.android.photo_picker.legacy.E;
import com.avito.android.select.SegmentedControlRedesign;
import com.avito.android.util.D6;
import com.avito.android.visual_rubricator.C36116g;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.my.target.Q0;
import com.my.target.s1;
import com.my.tracker.obfuscated.n1;
import ru.cyberity.cbr.core.presentation.form.FormFragment;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class q implements Runnable {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f25024b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f25025c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Object f25026d;

    public /* synthetic */ q(int i12, int i13, Object obj) {
        this.f25024b = i13;
        this.f25026d = obj;
        this.f25025c = i12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i12 = 3;
        boolean zBringPointIntoView = false;
        boolean zBringPointIntoView2 = false;
        int i13 = this.f25025c;
        Object obj = this.f25026d;
        switch (this.f25024b) {
            case 0:
                r rVar = (r) obj;
                if (rVar.f25038l != i13) {
                    int i14 = rVar.f25034h;
                    rVar.f25038l = (i13 / i14) * i14;
                    C20140q0.d(3, "BufferedAudioStream");
                    break;
                }
                break;
            case 1:
                ((i.f) obj).b(i13);
                break;
            case 2:
                a.b bVar = new a.b(i13);
                com.avito.konveyor.adapter.j jVar = ((ShortVideosViewImpl) obj).f98504g;
                (jVar != null ? jVar : null).notifyItemChanged(i13, bVar);
                break;
            case 3:
                v vVar = (v) obj;
                Input input = vVar.f105564m;
                if (!input.f179319L) {
                    InputField inputField = input.f179340l;
                    zBringPointIntoView = inputField.bringPointIntoView(inputField.getSelectionStart());
                }
                if (zBringPointIntoView && i13 < 5) {
                    vVar.f105564m.postDelayed(new q(i13 + 1, i12, vVar), 300L);
                    break;
                }
                break;
            case 4:
                B b12 = (B) obj;
                Input input2 = b12.f105912d;
                if (!input2.f179319L) {
                    InputField inputField2 = input2.f179340l;
                    zBringPointIntoView2 = inputField2.bringPointIntoView(inputField2.getSelectionStart());
                }
                if (zBringPointIntoView2 && i13 < 5) {
                    b12.f105912d.postDelayed(new q(i13 + 1, 4, b12), 300L);
                    break;
                }
                break;
            case 5:
                c.i iVarJ = ((A) obj).f149264x.j(i13);
                if (iVarJ != null) {
                    iVarJ.b();
                    break;
                }
                break;
            case 6:
                ProfileSearchInputView profileSearchInputView = (ProfileSearchInputView) obj;
                Input input3 = profileSearchInputView.f153390h;
                int i15 = this.f25025c;
                Input.j(input3, 0, i15, 11);
                Input.e(profileSearchInputView.f153390h, 0, 0, i15, 0, 0, 27);
                break;
            case 7:
                D6.m(((com.avito.android.lib.design.description_list.e) obj).f179039c, i13);
                break;
            case 8:
                ((com.avito.android.messenger.map.d) obj).f196458l.p(i13);
                break;
            case 9:
                ((com.avito.android.mortgage.person_form.c) obj).f200492i.F0(i13);
                break;
            case 10:
                RecyclerView recyclerView = (RecyclerView) obj;
                RecyclerView.m layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                if (linearLayoutManager != null) {
                    linearLayoutManager.l1(i13);
                    P.b(recyclerView, new w(recyclerView, linearLayoutManager, i13, recyclerView));
                    break;
                }
                break;
            case 11:
                int i16 = PhotoGallery.f217675i;
                ((PhotoGallery) obj).getPager().x(i13, false);
                break;
            case 12:
                CarouselPhotoGalleryView.c((CarouselPhotoGalleryView) obj, i13);
                break;
            case 13:
                E e12 = (E) obj;
                RecyclerView recyclerView2 = e12.f219584g;
                RecyclerView.Adapter adapter = recyclerView2.getAdapter();
                int itemCount = adapter != null ? adapter.getItemCount() : 0;
                if (i13 > 0 && i13 < itemCount - 1) {
                    LinearLayoutManager linearLayoutManager2 = e12.f219580c;
                    int iH1 = linearLayoutManager2.H1();
                    int iL1 = linearLayoutManager2.L1() - iH1;
                    if (iL1 != 0) {
                        i13 = ((double) (i13 - iH1)) / ((double) iL1) < 0.5d ? i13 - 1 : i13 + 1;
                    }
                }
                recyclerView2.F0(i13);
                break;
            case 14:
                ((com.avito.android.publish.screen.step.params.view.j) obj).f241618p.F0(i13);
                break;
            case 15:
                int i17 = SegmentedControlRedesign.f264969h;
                ((SegmentedControlRedesign) obj).c(i13, false);
                break;
            case 16:
                RecyclerView recyclerView3 = ((com.avito.android.verification.verification_form_builder.m) obj).f325403e;
                com.avito.android.verification.verification_form_builder.p pVar = new com.avito.android.verification.verification_form_builder.p(recyclerView3.getContext());
                pVar.f53878a = i13;
                RecyclerView.m layoutManager2 = recyclerView3.getLayoutManager();
                if (layoutManager2 != null) {
                    layoutManager2.x1(pVar);
                    break;
                }
                break;
            case 17:
                HorizontalScrollView horizontalScrollView = ((C36116g) obj).f327419e;
                horizontalScrollView.scrollTo(i13, 0);
                D6.H(horizontalScrollView);
                break;
            case 18:
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) obj;
                View view = (View) sideSheetBehavior.f357169o.get();
                if (view != null) {
                    sideSheetBehavior.x(view, i13, false);
                    break;
                }
                break;
            case 19:
                ((n1) obj).c(i13);
                break;
            case 20:
                Q0.b(Q0.this, i13);
                break;
            case 21:
                s1 s1Var = (s1) obj;
                if (i13 == -2 || i13 == -1) {
                    s1Var.d();
                    break;
                }
                break;
            default:
                FormFragment.m281validateForm$lambda6((FormFragment) obj, i13);
                break;
        }
    }

    public /* synthetic */ q(int i12, ShortVideosViewImpl shortVideosViewImpl) {
        this.f25024b = 2;
        this.f25025c = i12;
        this.f25026d = shortVideosViewImpl;
    }
}
