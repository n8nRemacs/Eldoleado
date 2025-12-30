package Ac0;

import com.avito.android.edit_carousel.entity.CarouselEditorData;
import com.avito.android.extended_profile_personal_link_edit.PersonalLinkEditConfig;
import com.avito.android.extended_profile_selection_create.ExtendedProfileSelectionCreateConfig;
import com.avito.android.extended_profile_universal_widget_edit.UniversalWidgetCreateArguments;
import com.avito.android.extended_profile_universal_widget_edit.UniversalWidgetEditArguments;
import com.avito.android.remote.model.text.AttributedText;
import java.util.List;
import kotlin.Metadata;

/* compiled from: ExtendedProfileSettingsRouter.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\b`\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAc0/d;", "", "_avito_profile-management_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Ac0.d, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public interface InterfaceC13011d {
    void a(@Y61.k CarouselEditorData carouselEditorData);

    void b(@Y61.k ExtendedProfileSelectionCreateConfig extendedProfileSelectionCreateConfig);

    void c(@Y61.k UniversalWidgetEditArguments universalWidgetEditArguments);

    void d();

    void e(int i12, @Y61.k List list);

    void f(@Y61.k PersonalLinkEditConfig personalLinkEditConfig);

    void g(int i12, int i13, @Y61.k String str, @Y61.k String str2, @Y61.k String str3);

    void h(@Y61.k String str);

    void i(int i12, @Y61.k String str);

    void j(@Y61.k AttributedText attributedText, @Y61.k String str, @Y61.k String str2);

    void k(@Y61.k UniversalWidgetCreateArguments universalWidgetCreateArguments);
}
