"""
Fingerprint Manager - stable browser fingerprints.

Each account has ONE fingerprint that NEVER changes.
This ensures Avito sees the same "device" every time.
"""

import json
import random
import logging
from datetime import datetime
from pathlib import Path
from typing import Dict, Any, List

logger = logging.getLogger(__name__)

# Common screen resolutions
SCREEN_RESOLUTIONS = [
    (1920, 1080),
    (1366, 768),
    (1536, 864),
    (1440, 900),
    (1280, 720),
]

# Common WebGL renderers
WEBGL_RENDERERS = [
    ("Google Inc. (NVIDIA)", "ANGLE (NVIDIA, NVIDIA GeForce GTX 1060 6GB Direct3D11 vs_5_0 ps_5_0)"),
    ("Google Inc. (NVIDIA)", "ANGLE (NVIDIA, NVIDIA GeForce GTX 1650 Direct3D11 vs_5_0 ps_5_0)"),
    ("Google Inc. (NVIDIA)", "ANGLE (NVIDIA, NVIDIA GeForce RTX 3060 Direct3D11 vs_5_0 ps_5_0)"),
    ("Google Inc. (Intel)", "ANGLE (Intel, Intel(R) UHD Graphics 630 Direct3D11 vs_5_0 ps_5_0)"),
    ("Google Inc. (AMD)", "ANGLE (AMD, AMD Radeon RX 580 Series Direct3D11 vs_5_0 ps_5_0)"),
]

# Common fonts
COMMON_FONTS = [
    "Arial",
    "Times New Roman",
    "Courier New",
    "Georgia",
    "Verdana",
    "Tahoma",
    "Trebuchet MS",
    "Impact",
    "Comic Sans MS",
    "Lucida Console",
]


class FingerprintManager:
    """Manages stable browser fingerprints."""

    def __init__(self, account_dir: Path):
        self.account_dir = account_dir
        self.fingerprint_file = account_dir / "fingerprint.json"

    def get_or_create(self) -> Dict[str, Any]:
        """Get existing fingerprint or create new one."""
        if self.fingerprint_file.exists():
            return self._load()
        return self._create()

    def _load(self) -> Dict[str, Any]:
        """Load fingerprint from disk."""
        try:
            data = json.loads(self.fingerprint_file.read_text())
            logger.info(f"Loaded fingerprint from {self.fingerprint_file}")
            return data
        except Exception as e:
            logger.error(f"Error loading fingerprint: {e}")
            return self._create()

    def _create(self) -> Dict[str, Any]:
        """Create new fingerprint and save to disk."""
        # Random screen resolution
        width, height = random.choice(SCREEN_RESOLUTIONS)

        # Random WebGL
        webgl_vendor, webgl_renderer = random.choice(WEBGL_RENDERERS)

        # Random subset of fonts
        fonts = random.sample(COMMON_FONTS, k=random.randint(6, 10))

        # Firefox user agent (matches Camoufox)
        firefox_version = random.randint(115, 125)
        user_agent = (
            f"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:{firefox_version}.0) "
            f"Gecko/20100101 Firefox/{firefox_version}.0"
        )

        fingerprint = {
            "created_at": datetime.now().isoformat(),
            "screen": {
                "width": width,
                "height": height,
                "colorDepth": 24,
                "pixelDepth": 24,
            },
            "userAgent": user_agent,
            "platform": "Win32",
            "language": "ru-RU",
            "languages": ["ru-RU", "ru", "en-US", "en"],
            "timezone": "Europe/Moscow",
            "timezoneOffset": -180,
            "webgl": {
                "vendor": webgl_vendor,
                "renderer": webgl_renderer,
            },
            "canvas_seed": random.randint(1, 1000000),
            "audio_seed": random.uniform(0.0001, 0.0002),
            "fonts": fonts,
            "hardware": {
                "cpuCores": random.choice([4, 6, 8, 12]),
                "memory": random.choice([4, 8, 16]),
                "maxTouchPoints": 0,
            },
            "doNotTrack": random.choice([None, "1"]),
            "cookieEnabled": True,
        }

        # Save to disk
        self._save(fingerprint)
        logger.info(f"Created new fingerprint: {self.fingerprint_file}")

        return fingerprint

    def _save(self, fingerprint: Dict[str, Any]) -> None:
        """Save fingerprint to disk."""
        self.account_dir.mkdir(parents=True, exist_ok=True)
        self.fingerprint_file.write_text(json.dumps(fingerprint, indent=2, ensure_ascii=False))

    def get_camoufox_config(self) -> Dict[str, Any]:
        """Get fingerprint as Camoufox config."""
        fp = self.get_or_create()

        return {
            "screen": fp["screen"],
            "navigator": {
                "userAgent": fp["userAgent"],
                "platform": fp["platform"],
                "language": fp["language"],
                "languages": fp["languages"],
                "hardwareConcurrency": fp["hardware"]["cpuCores"],
                "deviceMemory": fp["hardware"]["memory"],
                "maxTouchPoints": fp["hardware"]["maxTouchPoints"],
                "doNotTrack": fp["doNotTrack"],
                "cookieEnabled": fp["cookieEnabled"],
            },
            "timezone": {
                "id": fp["timezone"],
                "offset": fp["timezoneOffset"],
            },
            "webgl": fp["webgl"],
        }
