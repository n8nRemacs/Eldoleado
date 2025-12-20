"""Expected response schemas for validation."""

from typing import Any, Dict, List, Optional, Set, Tuple


# Expected fields in /health/extended response
HealthExtendedSchema = {
    "required": ["status", "channel", "version"],
    "optional": ["health_score", "uptime_seconds", "sessions", "totals"],
    "types": {
        "status": str,
        "channel": str,
        "version": str,
        "health_score": (int, float),
        "uptime_seconds": (int, float),
    }
}


def validate_response(
    response: Dict[str, Any],
    schema: Dict[str, Any]
) -> Tuple[bool, Optional[str]]:
    """
    Validate response against expected schema.

    Returns:
        Tuple of (is_valid, error_message)
    """
    if not isinstance(response, dict):
        return False, "Response is not a dictionary"

    # Check required fields
    required = schema.get("required", [])
    missing = [f for f in required if f not in response]
    if missing:
        return False, f"Missing required fields: {missing}"

    # Check types
    types = schema.get("types", {})
    for field, expected_type in types.items():
        if field in response:
            value = response[field]
            if not isinstance(value, expected_type):
                return False, f"Field '{field}' has wrong type: expected {expected_type}, got {type(value)}"

    return True, None


def get_schema_diff(
    expected: Dict[str, Any],
    actual: Dict[str, Any]
) -> Optional[str]:
    """
    Compare actual response structure with expected.

    Returns diff string if there are differences, None otherwise.
    """
    if not isinstance(actual, dict):
        return "Response is not a dictionary"

    expected_keys = set(expected.get("required", []) + expected.get("optional", []))
    actual_keys = set(actual.keys())

    # Find new fields (not in expected)
    new_fields = actual_keys - expected_keys
    # Find missing required fields
    missing_fields = set(expected.get("required", [])) - actual_keys

    if not new_fields and not missing_fields:
        return None

    diff_parts = []
    if missing_fields:
        diff_parts.append(f"Missing: {list(missing_fields)}")
    if new_fields:
        diff_parts.append(f"New: {list(new_fields)}")

    return "; ".join(diff_parts)


def extract_schema(response: Dict[str, Any]) -> Dict[str, Any]:
    """Extract schema from actual response for learning/updating."""
    if not isinstance(response, dict):
        return {}

    schema = {
        "fields": list(response.keys()),
        "types": {k: type(v).__name__ for k, v in response.items()},
    }
    return schema
