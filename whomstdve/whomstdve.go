package whomstdve

import (
	"fmt"
)

type WhomstdveParams struct {
	Name string
}

func SayWhomstdve(params *WhomstdveParams) string {
	var name string
	if params != nil {
		name = params.Name
	} else {
		name = "whomstdve"
	}
	return fmt.Sprintf("whomstdve, %s", name)
}
