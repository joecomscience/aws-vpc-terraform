package pkg

import (
	"bytes"
	"crypto/tls"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
)

var (
	postToken = "Token " + os.Getenv("POST_TOKEN")
)

type token struct {
	Expire string
	Token  string
}

type PostItem struct {
	Status   string   `json:"status"`
	Language string   `json:"language"`
	Barcode  []string `json:"barcode"`
}

type itemRes struct{}

type Post struct {
	Token string
}

func (p *Post) GetToken() {
	url := os.Getenv("POST_URL") + "/post/api/v1/authenticate/token"
	t := token{}

	tr := &http.Transport{
		TLSClientConfig: &tls.Config{InsecureSkipVerify: true},
	}
	c := &http.Client{Transport: tr}

	req, _ := http.NewRequest("POST", url, nil)
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Authorization", postToken)

	r, err := c.Do(req)
	if err != nil {
		log.Printf("Get token from thai post error: %v\n", err)
		return
	}
	defer r.Body.Close()
	json.NewDecoder(r.Body).Decode(&t)

	p.Token = "Token " + t.Token

	fmt.Println(p.Token)
}

func (p *Post) GetItems(item PostItem) error {
	url := os.Getenv("POST_URL") + "/post/api/v1/track"
	value, _ := json.Marshal(item)
	tr := &http.Transport{
		TLSClientConfig: &tls.Config{InsecureSkipVerify: true},
	}
	c := &http.Client{Transport: tr}

	req, _ := http.NewRequest("POST", url, bytes.NewBuffer(value))
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Authorization", p.Token)

	log.Printf("token: %v\n", p.Token)

	r, err := c.Do(req)
	if err != nil {
		return err
	}
	defer r.Body.Close()

	x, _ := ioutil.ReadAll(r.Body)
	log.Printf("result: %v\n", string(x))
	return nil
}
