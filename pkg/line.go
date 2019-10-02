package pkg

import (
	"bytes"
	"net/http"
	"net/url"
	"os"
)

type Line struct {
	Message string
}

func (l *Line) Send() error {
	token := "Bearer " + os.Getenv("LINE_TOKEN")

	c := &http.Client{}
	b := url.Values{}
	b.Set("message", l.Message)

	r, _ := http.NewRequest("POST", os.Getenv("LINE_URL"), bytes.NewBufferString(b.Encode()))
	r.Header.Add("Content-Type", "application/json")
	r.Header.Add("Autherization", token)

	_, err := c.Do(r)
	if err != nil {
		return err
	}

	return nil
}
