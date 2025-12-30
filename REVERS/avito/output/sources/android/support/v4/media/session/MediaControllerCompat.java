package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import androidx.annotation.RestrictTo;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioAttributesImplApi26;
import androidx.versionedparcelable.c;
import androidx.versionedparcelable.h;
import com.avito.android.R;
import j.B;
import j.N;
import j.P;
import j.X;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class MediaControllerCompat {

    @RestrictTo
    public static final String COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM";

    @RestrictTo
    public static final String COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT";

    @RestrictTo
    public static final String COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX";

    @RestrictTo
    public static final String COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION";

    @RestrictTo
    public static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";

    @RestrictTo
    public static final String COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM";

    @RestrictTo
    public static final String COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT";
    static final String TAG = "MediaControllerCompat";
    private final MediaControllerImpl mImpl;

    @SuppressLint({"BanConcurrentHashMap"})
    private final ConcurrentHashMap<Callback, Boolean> mRegisteredCallbacks = new ConcurrentHashMap<>();
    private final MediaSessionCompat.Token mToken;

    public interface MediaControllerImpl {
        void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

        void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i12);

        void adjustVolume(int i12, int i13);

        boolean dispatchMediaButtonEvent(KeyEvent keyEvent);

        Bundle getExtras();

        long getFlags();

        Object getMediaController();

        MediaMetadataCompat getMetadata();

        String getPackageName();

        PlaybackInfo getPlaybackInfo();

        PlaybackStateCompat getPlaybackState();

        List<MediaSessionCompat.QueueItem> getQueue();

        CharSequence getQueueTitle();

        int getRatingType();

        int getRepeatMode();

        PendingIntent getSessionActivity();

        Bundle getSessionInfo();

        int getShuffleMode();

        TransportControls getTransportControls();

        boolean isCaptioningEnabled();

        boolean isSessionReady();

        void registerCallback(Callback callback, Handler handler);

        void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

        void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver);

        void setVolumeTo(int i12, int i13);

        void unregisterCallback(Callback callback);
    }

    @X
    public static class MediaControllerImplApi21 implements MediaControllerImpl {
        protected final MediaController mControllerFwk;
        protected Bundle mSessionInfo;
        final MediaSessionCompat.Token mSessionToken;
        final Object mLock = new Object();

        @B
        private final List<Callback> mPendingCallbacks = new ArrayList();
        private HashMap<Callback, ExtraCallback> mCallbackMap = new HashMap<>();

        public static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            private WeakReference<MediaControllerImplApi21> mMediaControllerImpl;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21) {
                super(null);
                this.mMediaControllerImpl = new WeakReference<>(mediaControllerImplApi21);
            }

            @Override // android.os.ResultReceiver
            public void onReceiveResult(int i12, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = this.mMediaControllerImpl.get();
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return;
                }
                synchronized (mediaControllerImplApi21.mLock) {
                    mediaControllerImplApi21.mSessionToken.setExtraBinder(IMediaSession.Stub.asInterface(bundle.getBinder(MediaSessionCompat.KEY_EXTRA_BINDER)));
                    mediaControllerImplApi21.mSessionToken.setSession2Token(c.a(bundle));
                    mediaControllerImplApi21.processPendingCallbacksLocked();
                }
            }
        }

        public static class ExtraCallback extends Callback.StubCompat {
            public ExtraCallback(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.MediaControllerCompat.Callback.StubCompat, android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                throw new AssertionError();
            }
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) {
            this.mSessionToken = token;
            this.mControllerFwk = new MediaController(context, (MediaSession.Token) token.getToken());
            if (token.getExtraBinder() == null) {
                requestExtraBinder();
            }
        }

        private void requestExtraBinder() {
            sendCommand(MediaControllerCompat.COMMAND_GET_EXTRA_BINDER, null, new ExtraBinderRequestResultReceiver(this));
        }

        public static void setMediaController(@N Activity activity, @P MediaControllerCompat mediaControllerCompat) {
            activity.setMediaController(mediaControllerCompat != null ? new MediaController(activity, (MediaSession.Token) mediaControllerCompat.getSessionToken().getToken()) : null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
            sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM, bundle, null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void adjustVolume(int i12, int i13) {
            this.mControllerFwk.adjustVolume(i12, i13);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            return this.mControllerFwk.dispatchMediaButtonEvent(keyEvent);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Bundle getExtras() {
            return this.mControllerFwk.getExtras();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public long getFlags() {
            return this.mControllerFwk.getFlags();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Object getMediaController() {
            return this.mControllerFwk;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public MediaMetadataCompat getMetadata() {
            MediaMetadata metadata = this.mControllerFwk.getMetadata();
            if (metadata != null) {
                return MediaMetadataCompat.fromMediaMetadata(metadata);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public String getPackageName() {
            return this.mControllerFwk.getPackageName();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PlaybackInfo getPlaybackInfo() {
            MediaController.PlaybackInfo playbackInfo = this.mControllerFwk.getPlaybackInfo();
            if (playbackInfo != null) {
                return new PlaybackInfo(playbackInfo.getPlaybackType(), new AudioAttributesCompat(new AudioAttributesImplApi26(playbackInfo.getAudioAttributes())), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume());
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PlaybackStateCompat getPlaybackState() {
            if (this.mSessionToken.getExtraBinder() != null) {
                try {
                    return this.mSessionToken.getExtraBinder().getPlaybackState();
                } catch (RemoteException unused) {
                }
            }
            PlaybackState playbackState = this.mControllerFwk.getPlaybackState();
            if (playbackState != null) {
                return PlaybackStateCompat.fromPlaybackState(playbackState);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public List<MediaSessionCompat.QueueItem> getQueue() {
            List<MediaSession.QueueItem> queue = this.mControllerFwk.getQueue();
            if (queue != null) {
                return MediaSessionCompat.QueueItem.fromQueueItemList(queue);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public CharSequence getQueueTitle() {
            return this.mControllerFwk.getQueueTitle();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getRatingType() {
            return this.mControllerFwk.getRatingType();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getRepeatMode() {
            if (this.mSessionToken.getExtraBinder() == null) {
                return -1;
            }
            try {
                return this.mSessionToken.getExtraBinder().getRepeatMode();
            } catch (RemoteException unused) {
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PendingIntent getSessionActivity() {
            return this.mControllerFwk.getSessionActivity();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Bundle getSessionInfo() {
            if (this.mSessionInfo != null) {
                return new Bundle(this.mSessionInfo);
            }
            if (this.mSessionToken.getExtraBinder() != null) {
                try {
                    this.mSessionInfo = this.mSessionToken.getExtraBinder().getSessionInfo();
                } catch (RemoteException unused) {
                    this.mSessionInfo = Bundle.EMPTY;
                }
            }
            Bundle bundleUnparcelWithClassLoader = MediaSessionCompat.unparcelWithClassLoader(this.mSessionInfo);
            this.mSessionInfo = bundleUnparcelWithClassLoader;
            return bundleUnparcelWithClassLoader == null ? Bundle.EMPTY : new Bundle(this.mSessionInfo);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getShuffleMode() {
            if (this.mSessionToken.getExtraBinder() == null) {
                return -1;
            }
            try {
                return this.mSessionToken.getExtraBinder().getShuffleMode();
            } catch (RemoteException unused) {
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public TransportControls getTransportControls() {
            MediaController.TransportControls transportControls = this.mControllerFwk.getTransportControls();
            return Build.VERSION.SDK_INT >= 29 ? new TransportControlsApi29(transportControls) : new TransportControlsApi24(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean isCaptioningEnabled() {
            if (this.mSessionToken.getExtraBinder() == null) {
                return false;
            }
            try {
                return this.mSessionToken.getExtraBinder().isCaptioningEnabled();
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean isSessionReady() {
            return this.mSessionToken.getExtraBinder() != null;
        }

        @B
        public void processPendingCallbacksLocked() {
            if (this.mSessionToken.getExtraBinder() == null) {
                return;
            }
            for (Callback callback : this.mPendingCallbacks) {
                ExtraCallback extraCallback = new ExtraCallback(callback);
                this.mCallbackMap.put(callback, extraCallback);
                callback.mIControllerCallback = extraCallback;
                try {
                    this.mSessionToken.getExtraBinder().registerCallbackListener(extraCallback);
                    callback.postToHandler(13, null, null);
                } catch (RemoteException unused) {
                }
            }
            this.mPendingCallbacks.clear();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public final void registerCallback(Callback callback, Handler handler) {
            this.mControllerFwk.registerCallback(callback.mCallbackFwk, handler);
            synchronized (this.mLock) {
                if (this.mSessionToken.getExtraBinder() != null) {
                    ExtraCallback extraCallback = new ExtraCallback(callback);
                    this.mCallbackMap.put(callback, extraCallback);
                    callback.mIControllerCallback = extraCallback;
                    try {
                        this.mSessionToken.getExtraBinder().registerCallbackListener(extraCallback);
                        callback.postToHandler(13, null, null);
                    } catch (RemoteException unused) {
                    }
                } else {
                    callback.mIControllerCallback = null;
                    this.mPendingCallbacks.add(callback);
                }
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
            sendCommand(MediaControllerCompat.COMMAND_REMOVE_QUEUE_ITEM, bundle, null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            this.mControllerFwk.sendCommand(str, bundle, resultReceiver);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void setVolumeTo(int i12, int i13) {
            this.mControllerFwk.setVolumeTo(i12, i13);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public final void unregisterCallback(Callback callback) {
            this.mControllerFwk.unregisterCallback(callback.mCallbackFwk);
            synchronized (this.mLock) {
                if (this.mSessionToken.getExtraBinder() != null) {
                    try {
                        ExtraCallback extraCallbackRemove = this.mCallbackMap.remove(callback);
                        if (extraCallbackRemove != null) {
                            callback.mIControllerCallback = null;
                            this.mSessionToken.getExtraBinder().unregisterCallbackListener(extraCallbackRemove);
                        }
                    } catch (RemoteException unused) {
                    }
                } else {
                    this.mPendingCallbacks.remove(callback);
                }
            }
        }

        @P
        public static MediaControllerCompat getMediaController(@N Activity activity) {
            MediaController mediaController = activity.getMediaController();
            if (mediaController == null) {
                return null;
            }
            return new MediaControllerCompat(activity, MediaSessionCompat.Token.fromToken(mediaController.getSessionToken()));
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i12) {
            if ((getFlags() & 4) != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaControllerCompat.COMMAND_ARGUMENT_MEDIA_DESCRIPTION, mediaDescriptionCompat);
                bundle.putInt(MediaControllerCompat.COMMAND_ARGUMENT_INDEX, i12);
                sendCommand(MediaControllerCompat.COMMAND_ADD_QUEUE_ITEM_AT, bundle, null);
                return;
            }
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }
    }

    @X
    public static class MediaControllerImplApi29 extends MediaControllerImplApi21 {
        public MediaControllerImplApi29(Context context, MediaSessionCompat.Token token) {
            super(context, token);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Bundle getSessionInfo() {
            if (this.mSessionInfo != null) {
                return new Bundle(this.mSessionInfo);
            }
            Bundle sessionInfo = this.mControllerFwk.getSessionInfo();
            this.mSessionInfo = sessionInfo;
            Bundle bundleUnparcelWithClassLoader = MediaSessionCompat.unparcelWithClassLoader(sessionInfo);
            this.mSessionInfo = bundleUnparcelWithClassLoader;
            return bundleUnparcelWithClassLoader == null ? Bundle.EMPTY : new Bundle(this.mSessionInfo);
        }
    }

    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        private final AudioAttributesCompat mAudioAttrsCompat;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        public PlaybackInfo(int i12, int i13, int i14, int i15, int i16) {
            AudioAttributesCompat.d dVar = new AudioAttributesCompat.d();
            dVar.d(i13);
            this(i12, dVar.a(), i14, i15, i16);
        }

        @N
        public AudioAttributesCompat getAudioAttributes() {
            return this.mAudioAttrsCompat;
        }

        @Deprecated
        public int getAudioStream() {
            return this.mAudioAttrsCompat.f46989a.b();
        }

        public int getCurrentVolume() {
            return this.mCurrentVolume;
        }

        public int getMaxVolume() {
            return this.mMaxVolume;
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getVolumeControl() {
            return this.mVolumeControl;
        }

        public PlaybackInfo(int i12, @N AudioAttributesCompat audioAttributesCompat, int i13, int i14, int i15) {
            this.mPlaybackType = i12;
            this.mAudioAttrsCompat = audioAttributesCompat;
            this.mVolumeControl = i13;
            this.mMaxVolume = i14;
            this.mCurrentVolume = i15;
        }
    }

    @X
    public static class TransportControlsApi21 extends TransportControls {
        protected final MediaController.TransportControls mControlsFwk;

        public TransportControlsApi21(MediaController.TransportControls transportControls) {
            this.mControlsFwk = transportControls;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void fastForward() {
            this.mControlsFwk.fastForward();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void pause() {
            this.mControlsFwk.pause();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void play() {
            this.mControlsFwk.play();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromMediaId(String str, Bundle bundle) {
            this.mControlsFwk.playFromMediaId(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromSearch(String str, Bundle bundle) {
            this.mControlsFwk.playFromSearch(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            if (uri == null || Uri.EMPTY.equals(uri)) {
                throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI, uri);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PLAY_FROM_URI, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE, (Bundle) null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID, str);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(MediaSessionCompat.ACTION_ARGUMENT_QUERY, str);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI, uri);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_PREPARE_FROM_URI, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void rewind() {
            this.mControlsFwk.rewind();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void seekTo(long j12) {
            this.mControlsFwk.seekTo(j12);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(customAction.getAction(), bundle);
            this.mControlsFwk.sendCustomAction(customAction.getAction(), bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setCaptioningEnabled(boolean z12) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED, z12);
            sendCustomAction(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f12) {
            if (f12 == 0.0f) {
                throw new IllegalArgumentException("speed must not be zero");
            }
            Bundle bundle = new Bundle();
            bundle.putFloat(MediaSessionCompat.ACTION_ARGUMENT_PLAYBACK_SPEED, f12);
            sendCustomAction(MediaSessionCompat.ACTION_SET_PLAYBACK_SPEED, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat) {
            this.mControlsFwk.setRating(ratingCompat != null ? (Rating) ratingCompat.getRating() : null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRepeatMode(int i12) {
            sendCustomAction(MediaSessionCompat.ACTION_SET_REPEAT_MODE, AK.c.e(i12, MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE));
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setShuffleMode(int i12) {
            sendCustomAction(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE, AK.c.e(i12, MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE));
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToNext() {
            this.mControlsFwk.skipToNext();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToPrevious() {
            this.mControlsFwk.skipToPrevious();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToQueueItem(long j12) {
            this.mControlsFwk.skipToQueueItem(j12);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void stop() {
            this.mControlsFwk.stop();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING, ratingCompat);
            bundle2.putBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS, bundle);
            sendCustomAction(MediaSessionCompat.ACTION_SET_RATING, bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(str, bundle);
            this.mControlsFwk.sendCustomAction(str, bundle);
        }
    }

    @X
    public static class TransportControlsApi23 extends TransportControlsApi21 {
        public TransportControlsApi23(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            this.mControlsFwk.playFromUri(uri, bundle);
        }
    }

    @X
    public static class TransportControlsApi24 extends TransportControlsApi23 {
        public TransportControlsApi24(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            this.mControlsFwk.prepare();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            this.mControlsFwk.prepareFromMediaId(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            this.mControlsFwk.prepareFromSearch(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            this.mControlsFwk.prepareFromUri(uri, bundle);
        }
    }

    @X
    public static class TransportControlsApi29 extends TransportControlsApi24 {
        public TransportControlsApi29(MediaController.TransportControls transportControls) {
            super(transportControls);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControlsApi21, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f12) {
            if (f12 == 0.0f) {
                throw new IllegalArgumentException("speed must not be zero");
            }
            this.mControlsFwk.setPlaybackSpeed(f12);
        }
    }

    public static class TransportControlsBase extends TransportControls {
        private IMediaSession mBinder;

        public TransportControlsBase(IMediaSession iMediaSession) {
            this.mBinder = iMediaSession;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void fastForward() {
            try {
                this.mBinder.fastForward();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void pause() {
            try {
                this.mBinder.pause();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void play() {
            try {
                this.mBinder.play();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromMediaId(String str, Bundle bundle) {
            try {
                this.mBinder.playFromMediaId(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromSearch(String str, Bundle bundle) {
            try {
                this.mBinder.playFromSearch(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            try {
                this.mBinder.playFromUri(uri, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            try {
                this.mBinder.prepare();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            try {
                this.mBinder.prepareFromMediaId(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            try {
                this.mBinder.prepareFromSearch(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            try {
                this.mBinder.prepareFromUri(uri, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void rewind() {
            try {
                this.mBinder.rewind();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void seekTo(long j12) {
            try {
                this.mBinder.seekTo(j12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            sendCustomAction(customAction.getAction(), bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setCaptioningEnabled(boolean z12) {
            try {
                this.mBinder.setCaptioningEnabled(z12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setPlaybackSpeed(float f12) {
            if (f12 == 0.0f) {
                throw new IllegalArgumentException("speed must not be zero");
            }
            try {
                this.mBinder.setPlaybackSpeed(f12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat) {
            try {
                this.mBinder.rate(ratingCompat);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRepeatMode(int i12) {
            try {
                this.mBinder.setRepeatMode(i12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setShuffleMode(int i12) {
            try {
                this.mBinder.setShuffleMode(i12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToNext() {
            try {
                this.mBinder.next();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToPrevious() {
            try {
                this.mBinder.previous();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToQueueItem(long j12) {
            try {
                this.mBinder.skipToQueueItem(j12);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void stop() {
            try {
                this.mBinder.stop();
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.validateCustomAction(str, bundle);
            try {
                this.mBinder.sendCustomAction(str, bundle);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            try {
                this.mBinder.rateWithExtras(ratingCompat, bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    public MediaControllerCompat(Context context, @N MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        MediaSessionCompat.Token sessionToken = mediaSessionCompat.getSessionToken();
        this.mToken = sessionToken;
        if (Build.VERSION.SDK_INT >= 29) {
            this.mImpl = new MediaControllerImplApi29(context, sessionToken);
        } else {
            this.mImpl = new MediaControllerImplApi21(context, sessionToken);
        }
    }

    public static MediaControllerCompat getMediaController(@N Activity activity) {
        Object tag = activity.getWindow().getDecorView().getTag(R.id.media_controller_compat_view_tag);
        return tag instanceof MediaControllerCompat ? (MediaControllerCompat) tag : MediaControllerImplApi21.getMediaController(activity);
    }

    public static void setMediaController(@N Activity activity, MediaControllerCompat mediaControllerCompat) {
        activity.getWindow().getDecorView().setTag(R.id.media_controller_compat_view_tag, mediaControllerCompat);
        MediaControllerImplApi21.setMediaController(activity, mediaControllerCompat);
    }

    public static void validateCustomAction(String str, Bundle bundle) {
        if (str == null) {
            return;
        }
        if (str.equals(MediaSessionCompat.ACTION_FOLLOW) || str.equals(MediaSessionCompat.ACTION_UNFOLLOW)) {
            if (bundle == null || !bundle.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                throw new IllegalArgumentException(AK.c.k("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action ", str, "."));
            }
        }
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.mImpl.addQueueItem(mediaDescriptionCompat);
    }

    public void adjustVolume(int i12, int i13) {
        this.mImpl.adjustVolume(i12, i13);
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.mImpl.dispatchMediaButtonEvent(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public Bundle getExtras() {
        return this.mImpl.getExtras();
    }

    public long getFlags() {
        return this.mImpl.getFlags();
    }

    public MediaMetadataCompat getMetadata() {
        return this.mImpl.getMetadata();
    }

    public String getPackageName() {
        return this.mImpl.getPackageName();
    }

    public PlaybackInfo getPlaybackInfo() {
        return this.mImpl.getPlaybackInfo();
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.mImpl.getPlaybackState();
    }

    public List<MediaSessionCompat.QueueItem> getQueue() {
        return this.mImpl.getQueue();
    }

    public CharSequence getQueueTitle() {
        return this.mImpl.getQueueTitle();
    }

    public int getRatingType() {
        return this.mImpl.getRatingType();
    }

    public int getRepeatMode() {
        return this.mImpl.getRepeatMode();
    }

    @RestrictTo
    @P
    public h getSession2Token() {
        return this.mToken.getSession2Token();
    }

    public PendingIntent getSessionActivity() {
        return this.mImpl.getSessionActivity();
    }

    @N
    public Bundle getSessionInfo() {
        return this.mImpl.getSessionInfo();
    }

    public MediaSessionCompat.Token getSessionToken() {
        return this.mToken;
    }

    public int getShuffleMode() {
        return this.mImpl.getShuffleMode();
    }

    public TransportControls getTransportControls() {
        return this.mImpl.getTransportControls();
    }

    public boolean isCaptioningEnabled() {
        return this.mImpl.isCaptioningEnabled();
    }

    public boolean isSessionReady() {
        return this.mImpl.isSessionReady();
    }

    public void registerCallback(@N Callback callback) {
        registerCallback(callback, null);
    }

    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.mImpl.removeQueueItem(mediaDescriptionCompat);
    }

    @Deprecated
    public void removeQueueItemAt(int i12) {
        MediaSessionCompat.QueueItem queueItem;
        List<MediaSessionCompat.QueueItem> queue = getQueue();
        if (queue == null || i12 < 0 || i12 >= queue.size() || (queueItem = queue.get(i12)) == null) {
            return;
        }
        removeQueueItem(queueItem.getDescription());
    }

    public void sendCommand(@N String str, @P Bundle bundle, @P ResultReceiver resultReceiver) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command must neither be null nor empty");
        }
        this.mImpl.sendCommand(str, bundle, resultReceiver);
    }

    public void setVolumeTo(int i12, int i13) {
        this.mImpl.setVolumeTo(i12, i13);
    }

    public void unregisterCallback(@N Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (this.mRegisteredCallbacks.remove(callback) == null) {
            return;
        }
        try {
            this.mImpl.unregisterCallback(callback);
        } finally {
            callback.setHandler(null);
        }
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i12) {
        this.mImpl.addQueueItem(mediaDescriptionCompat, i12);
    }

    public void registerCallback(@N Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (this.mRegisteredCallbacks.putIfAbsent(callback, Boolean.TRUE) != null) {
            return;
        }
        if (handler == null) {
            handler = new Handler();
        }
        callback.setHandler(handler);
        this.mImpl.registerCallback(callback, handler);
    }

    public static class MediaControllerImplBase implements MediaControllerImpl {
        private IMediaSession mBinder;
        private Bundle mSessionInfo;
        private TransportControls mTransportControls;

        public MediaControllerImplBase(MediaSessionCompat.Token token) {
            this.mBinder = IMediaSession.Stub.asInterface((IBinder) token.getToken());
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.mBinder.getFlags() & 4) == 0) {
                    throw new UnsupportedOperationException("This session doesn't support queue management operations");
                }
                this.mBinder.addQueueItem(mediaDescriptionCompat);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void adjustVolume(int i12, int i13) {
            try {
                this.mBinder.adjustVolume(i12, i13, null);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            if (keyEvent == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.mBinder.sendMediaButton(keyEvent);
                return false;
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Bundle getExtras() {
            try {
                return this.mBinder.getExtras();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public long getFlags() {
            try {
                return this.mBinder.getFlags();
            } catch (RemoteException unused) {
                return 0L;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Object getMediaController() {
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public MediaMetadataCompat getMetadata() {
            try {
                return this.mBinder.getMetadata();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public String getPackageName() {
            try {
                return this.mBinder.getPackageName();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PlaybackInfo getPlaybackInfo() {
            try {
                ParcelableVolumeInfo volumeAttributes = this.mBinder.getVolumeAttributes();
                return new PlaybackInfo(volumeAttributes.volumeType, volumeAttributes.audioStream, volumeAttributes.controlType, volumeAttributes.maxVolume, volumeAttributes.currentVolume);
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PlaybackStateCompat getPlaybackState() {
            try {
                return this.mBinder.getPlaybackState();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public List<MediaSessionCompat.QueueItem> getQueue() {
            try {
                return this.mBinder.getQueue();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public CharSequence getQueueTitle() {
            try {
                return this.mBinder.getQueueTitle();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getRatingType() {
            try {
                return this.mBinder.getRatingType();
            } catch (RemoteException unused) {
                return 0;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getRepeatMode() {
            try {
                return this.mBinder.getRepeatMode();
            } catch (RemoteException unused) {
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public PendingIntent getSessionActivity() {
            try {
                return this.mBinder.getLaunchPendingIntent();
            } catch (RemoteException unused) {
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public Bundle getSessionInfo() {
            try {
                this.mSessionInfo = this.mBinder.getSessionInfo();
            } catch (RemoteException unused) {
            }
            Bundle bundleUnparcelWithClassLoader = MediaSessionCompat.unparcelWithClassLoader(this.mSessionInfo);
            this.mSessionInfo = bundleUnparcelWithClassLoader;
            return bundleUnparcelWithClassLoader == null ? Bundle.EMPTY : new Bundle(this.mSessionInfo);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public int getShuffleMode() {
            try {
                return this.mBinder.getShuffleMode();
            } catch (RemoteException unused) {
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public TransportControls getTransportControls() {
            if (this.mTransportControls == null) {
                this.mTransportControls = new TransportControlsBase(this.mBinder);
            }
            return this.mTransportControls;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean isCaptioningEnabled() {
            try {
                return this.mBinder.isCaptioningEnabled();
            } catch (RemoteException unused) {
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public boolean isSessionReady() {
            return true;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void registerCallback(Callback callback, Handler handler) throws RemoteException {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.mBinder.asBinder().linkToDeath(callback, 0);
                this.mBinder.registerCallbackListener(callback.mIControllerCallback);
                callback.postToHandler(13, null, null);
            } catch (RemoteException unused) {
                callback.postToHandler(8, null, null);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            try {
                if ((this.mBinder.getFlags() & 4) == 0) {
                    throw new UnsupportedOperationException("This session doesn't support queue management operations");
                }
                this.mBinder.removeQueueItem(mediaDescriptionCompat);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void sendCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
            try {
                this.mBinder.sendCommand(str, bundle, resultReceiver == null ? null : new MediaSessionCompat.ResultReceiverWrapper(resultReceiver));
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void setVolumeTo(int i12, int i13) {
            try {
                this.mBinder.setVolumeTo(i12, i13, null);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void unregisterCallback(Callback callback) {
            if (callback == null) {
                throw new IllegalArgumentException("callback may not be null.");
            }
            try {
                this.mBinder.unregisterCallbackListener(callback.mIControllerCallback);
                this.mBinder.asBinder().unlinkToDeath(callback, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImpl
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i12) {
            try {
                if ((this.mBinder.getFlags() & 4) != 0) {
                    this.mBinder.addQueueItemAt(mediaDescriptionCompat, i12);
                    return;
                }
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            } catch (RemoteException unused) {
            }
        }
    }

    public Object getMediaController() {
        return this.mImpl.getMediaController();
    }

    public MediaControllerCompat(Context context, @N MediaSessionCompat.Token token) {
        if (token != null) {
            this.mToken = token;
            this.mImpl = new MediaControllerImplApi21(context, token);
            return;
        }
        throw new IllegalArgumentException("sessionToken must not be null");
    }

    public static abstract class Callback implements IBinder.DeathRecipient {
        final MediaController.Callback mCallbackFwk = new MediaControllerCallbackApi21(this);
        MessageHandler mHandler;
        IMediaControllerCallback mIControllerCallback;

        @X
        public static class MediaControllerCallbackApi21 extends MediaController.Callback {
            private final WeakReference<Callback> mCallback;

            public MediaControllerCallbackApi21(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }

            @Override // android.media.session.MediaController.Callback
            public void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onAudioInfoChanged(new PlaybackInfo(playbackInfo.getPlaybackType(), new AudioAttributesCompat(new AudioAttributesImplApi26(playbackInfo.getAudioAttributes())), playbackInfo.getVolumeControl(), playbackInfo.getMaxVolume(), playbackInfo.getCurrentVolume()));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onExtrasChanged(Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onExtrasChanged(bundle);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onMetadataChanged(MediaMetadata mediaMetadata) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(mediaMetadata));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onPlaybackStateChanged(PlaybackState playbackState) {
                Callback callback = this.mCallback.get();
                if (callback == null || callback.mIControllerCallback != null) {
                    return;
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(playbackState));
            }

            @Override // android.media.session.MediaController.Callback
            public void onQueueChanged(List<MediaSession.QueueItem> list) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list));
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence);
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionDestroyed() {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override // android.media.session.MediaController.Callback
            public void onSessionEvent(String str, Bundle bundle) {
                MediaSessionCompat.ensureClassLoader(bundle);
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.onSessionEvent(str, bundle);
                }
            }
        }

        public class MessageHandler extends Handler {
            private static final int MSG_DESTROYED = 8;
            private static final int MSG_EVENT = 1;
            private static final int MSG_SESSION_READY = 13;
            private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
            private static final int MSG_UPDATE_EXTRAS = 7;
            private static final int MSG_UPDATE_METADATA = 3;
            private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
            private static final int MSG_UPDATE_QUEUE = 5;
            private static final int MSG_UPDATE_QUEUE_TITLE = 6;
            private static final int MSG_UPDATE_REPEAT_MODE = 9;
            private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
            private static final int MSG_UPDATE_VOLUME = 4;
            boolean mRegistered;

            public MessageHandler(Looper looper) {
                super(looper);
                this.mRegistered = false;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (this.mRegistered) {
                    switch (message.what) {
                        case 1:
                            Bundle data = message.getData();
                            MediaSessionCompat.ensureClassLoader(data);
                            Callback.this.onSessionEvent((String) message.obj, data);
                            break;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            break;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            break;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj);
                            break;
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj);
                            break;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj);
                            break;
                        case 7:
                            Bundle bundle = (Bundle) message.obj;
                            MediaSessionCompat.ensureClassLoader(bundle);
                            Callback.this.onExtrasChanged(bundle);
                            break;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            break;
                        case 9:
                            Callback.this.onRepeatModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 11:
                            Callback.this.onCaptioningEnabledChanged(((Boolean) message.obj).booleanValue());
                            break;
                        case 12:
                            Callback.this.onShuffleModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 13:
                            Callback.this.onSessionReady();
                            break;
                    }
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            postToHandler(8, null, null);
        }

        @RestrictTo
        public IMediaControllerCallback getIControllerCallback() {
            return this.mIControllerCallback;
        }

        public void postToHandler(int i12, Object obj, Bundle bundle) {
            MessageHandler messageHandler = this.mHandler;
            if (messageHandler != null) {
                Message messageObtainMessage = messageHandler.obtainMessage(i12, obj);
                messageObtainMessage.setData(bundle);
                messageObtainMessage.sendToTarget();
            }
        }

        public void setHandler(Handler handler) {
            if (handler != null) {
                MessageHandler messageHandler = new MessageHandler(handler.getLooper());
                this.mHandler = messageHandler;
                messageHandler.mRegistered = true;
            } else {
                MessageHandler messageHandler2 = this.mHandler;
                if (messageHandler2 != null) {
                    messageHandler2.mRegistered = false;
                    messageHandler2.removeCallbacksAndMessages(null);
                    this.mHandler = null;
                }
            }
        }

        public void onSessionDestroyed() {
        }

        public void onSessionReady() {
        }

        public static class StubCompat extends IMediaControllerCallback.Stub {
            private final WeakReference<Callback> mCallback;

            public StubCompat(Callback callback) {
                this.mCallback = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onCaptioningEnabledChanged(boolean z12) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(11, Boolean.valueOf(z12), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onEvent(String str, Bundle bundle) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(1, str, bundle);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(7, bundle, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(3, mediaMetadataCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(2, playbackStateCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(5, list, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(6, charSequence, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onRepeatModeChanged(int i12) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(9, Integer.valueOf(i12), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(8, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionReady() {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(13, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChanged(int i12) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(12, Integer.valueOf(i12), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
                Callback callback = this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(4, parcelableVolumeInfo != null ? new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume) : null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChangedRemoved(boolean z12) {
            }
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onCaptioningEnabledChanged(boolean z12) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onRepeatModeChanged(int i12) {
        }

        public void onShuffleModeChanged(int i12) {
        }

        public void onSessionEvent(String str, Bundle bundle) {
        }
    }

    public static abstract class TransportControls {

        @Deprecated
        public static final String EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";

        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void prepare();

        public abstract void prepareFromMediaId(String str, Bundle bundle);

        public abstract void prepareFromSearch(String str, Bundle bundle);

        public abstract void prepareFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j12);

        public abstract void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setCaptioningEnabled(boolean z12);

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void setRating(RatingCompat ratingCompat, Bundle bundle);

        public abstract void setRepeatMode(int i12);

        public abstract void setShuffleMode(int i12);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j12);

        public abstract void stop();

        public void setPlaybackSpeed(float f12) {
        }
    }
}
