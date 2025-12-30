package Af0;

import androidx.compose.runtime.internal.P;
import com.avito.android.category_parameters.k;
import com.avito.android.publish.slots.vin_cropped_image.item.vin_cropped_image.f;
import com.avito.android.remote.model.category_parameters.slot.Slot;
import com.avito.android.remote.model.category_parameters.slot.vin_cropped_image.VinCroppedImageSlot;
import com.avito.android.remote.model.category_parameters.slot.vin_cropped_image.VinCroppedImageSlotConfig;
import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.C42784z0;

/* compiled from: VinCroppedImageSlotWrapper.kt */
@P
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"}, d2 = {"LAf0/a;", "Lcom/avito/android/category_parameters/k;", "Lcom/avito/android/remote/model/category_parameters/slot/vin_cropped_image/VinCroppedImageSlot;", "_avito_publish_impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* renamed from: Af0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final class C13027a extends k<VinCroppedImageSlot> {

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final VinCroppedImageSlot f548b;

    public C13027a(@Y61.k VinCroppedImageSlot vinCroppedImageSlot) {
        this.f548b = vinCroppedImageSlot;
    }

    @Override // com.avito.android.category_parameters.j
    public final Slot g() {
        return this.f548b;
    }

    @Override // com.avito.android.category_parameters.k
    @Y61.k
    public final List<com.avito.conveyor_item.a> j() {
        VinCroppedImageSlot vinCroppedImageSlot = this.f548b;
        String vinCroppedImage = ((VinCroppedImageSlotConfig) vinCroppedImageSlot.getWidget().getConfig()).getVinCroppedImage();
        return vinCroppedImage == null ? C42784z0.f406748b : Collections.singletonList(new f(vinCroppedImage, ((VinCroppedImageSlotConfig) vinCroppedImageSlot.getWidget().getConfig()).getText(), ((VinCroppedImageSlotConfig) vinCroppedImageSlot.getWidget().getConfig()).getLinkText()));
    }
}
