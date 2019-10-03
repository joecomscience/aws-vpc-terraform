package pkg

import (
	"bytes"
	"crypto/tls"
	"encoding/json"
	"fmt"
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

type ResponseItems struct {
	Response struct {
		Items map[string][]struct {
			Barcode             string `json:"barcode"`
			Status              string `json:"status"`
			StatusDescription   string `json:"status_description"`
			StatusDate          string `json:"status_date"`
			Location            string `json:"location"`
			Postcode            string `json:"postcode"`
			DeliveryStatus      string `json:"delivery_status,omitempty"`
			DeliveryDescription string `json:"delivery_description,omitempty"`
			DeliveryDatetime    string `json:"delivery_datetime,omitempty"`
			ReceiverName        string `json:"receiver_name,omitempty"`
			Signature           string `json:"signature,omitempty"`
		} `json:"items"`
	} `json:"response"`
}

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
	if err := json.NewDecoder(r.Body).Decode(&t); err != nil {
		log.Printf("json decoder error : %s \n", err)
		return
	}

	p.Token = "Token " + t.Token

	fmt.Println(p.Token)
}

func (p *Post) GetItems(item PostItem) (error, ResponseItems) {
	url := os.Getenv("POST_URL") + "/post/api/v1/track"
	value, _ := json.Marshal(item)
	res := ResponseItems{}
	tr := &http.Transport{
		TLSClientConfig: &tls.Config{InsecureSkipVerify: true},
	}
	c := &http.Client{Transport: tr}

	req, _ := http.NewRequest("POST", url, bytes.NewBuffer(value))
	req.Header.Add("Content-Type", "application/json")
	req.Header.Add("Authorization", p.Token)

	r, err := c.Do(req)
	if err != nil {
		return err, res
	}
	defer r.Body.Close()


	if err := json.NewDecoder(r.Body).Decode(&res); err != nil {
		log.Printf("err: %s \n", err)
		return err, res
	}
	return nil, res
}
