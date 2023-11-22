from unittest import TestCase

from src.utils import haversine

_EPSILON = 0.001


class UtilsTest(TestCase):
    def test_haversine(self):
        p = (37.498095, 127.027610)
        q = (37.499313, 127.026485)

        distance = haversine(p[0], p[1], q[0], q[1])

        assert abs(distance - 0.167) <= _EPSILON
