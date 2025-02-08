import http from "k6/http";

export let options = {
    scenarios: {
        default: {
            executor: 'per-vu-iterations',
            vus: 101,
            iterations: 2,
            maxDuration: '10m30s',
        },
    },
};

export default function () {
    const userId = __VU;
    const url = "http://localhost:8080/coupon/issue/"

    http.get(url + userId);
}

