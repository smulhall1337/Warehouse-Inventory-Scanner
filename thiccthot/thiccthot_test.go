package thiccthot

import (
	"testing"
)

func TestThiccthot(t *testing.T) {
	thot := New(true)
	w := ThotWhitelist{}
	if err := w.Add(thot); err == nil {
		t.Error(err)
	}
}
