import http from "k6/http"

export default function () {
    const url = "http://localhost:8080/coupon/issue/"

    let requests = [];
    for (let userId = 1; userId <= 101; userId++) {
        const request = {
            method: 'GET',
            url: url + userId
        };

        requests.push(request);
    }

    http.batch(requests);
}

