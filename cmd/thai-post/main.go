package main

import (
	"context"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"

	"github.com/joecomscience/thai-post-line-notify/pkg"
)

func main() {
	port := ":" + os.Getenv("PORT")

	router := http.NewServeMux()
	router.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
	})
	router.HandleFunc("/hook", hookHandler)
	router.HandleFunc("/status", statusHandler)

	srv := &http.Server{
		Addr:    port,
		Handler: router,
	}

	done := make(chan os.Signal, 1)
	signal.Notify(done, os.Interrupt, syscall.SIGINT, syscall.SIGTERM)

	go func() {
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("listen: %s", err)
		}
	}()
	log.Printf("server start using port: %s", port)

	<-done
	log.Println("Server Stopped")

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer func() {
		cancel()
	}()

	if err := srv.Shutdown(ctx); err != nil {
		log.Fatalf("Server Shutdown Failed:%+v", err)
	}
	log.Print("Server Exited Properly")

}

func hookHandler(w http.ResponseWriter, r *http.Request) {

}

func statusHandler(w http.ResponseWriter, r *http.Request) {
	post := pkg.Post{}
	postItem := pkg.PostItem{"all", "TH", []string{"EY145587896TH", "RC338848854TH"}}

	post.GetToken()
	post.GetItems(postItem)

	w.WriteHeader(http.StatusOK)
}
