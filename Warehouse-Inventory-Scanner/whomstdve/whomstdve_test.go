package whomstdve

import (
	"testing"
)

func TestWhomstdve(t *testing.T) {
	s := SayWhomstdve(nil)
	if s != "whomstdve, whomstdve" {
		t.Error()
	}
	s = SayWhomstdve(&WhomstdveParams{Name: "sean"})
	if s != "whomstdve, sean" {
		t.Error()
	}
}
