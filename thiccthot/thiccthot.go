package thiccthot

import (
	"errors"
)

type Thot struct {
	Thicc bool
}

type ThotWhitelist struct {
	Thots []*Thot
}

func New(thicc bool) *Thot {
	return &Thot{thicc}
}

func (l *ThotWhitelist) Add(t *Thot) error {
	if t.Thicc {
		return errors.New("no thicc thots allowed")
	}
	l.Thots = append(l.Thots, t)
	return nil
}
