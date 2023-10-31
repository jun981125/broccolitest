// 메인 슬라이더 설정
var scrollPosition = 1;
$(document).ready(function () {
    $('.flexslider').flexslider({
        animation: 'slide',
        selector: '.slides > li',
        controlsContainer: ".flexsliderwrapper",
    });

    // IntersectionObserver 설정
    var options = {
        root: null,
        rootMargin: '0px',
        threshold: 0.5,
    };

    var observer = new IntersectionObserver(callback, options);

    // 옵저버가 감시할 대상 설정
    var endList = document.getElementById('endList');
    observer.observe(endList);
});

// 상품 로드 함수
// 상품 로드 함수
function productLoad() {
    // 현재 페이지 번호 추출
    var currentPage = $("#startList .list_con").length / 8 + 1;

    // Ajax로 상품 로딩
    $.ajax({
        type: "GET",
        url: "/loadProducts",
     //   data: { scrollPosition: parseInt($(window).scrollTop()) },  // 스크롤 위치 전달
        data: { scrollPosition: scrollPosition},  // 스크롤 위치 전달
        // 가끔 실수로 전송되어, parseInt로 casting

            success: function (response) {
                // 받은 데이터를 이용하여 동적으로 HTML 생성
                var html = '';
                response.list.forEach(function(product) {
                    html += '<div class="list_con">' +
                        '    <div class="prod_img">';

                    if (product.pimage != null) {
                        // 이미지가 있는 경우
                        html += '        <a href="/showproduct?productid=' + product.productid + '&page=' + response.currentPage + '">';
                        html += '            <img src="/upload/' + product.pimage + '" />';
                        html += '        </a>';
                    } else {
                        // 이미지가 없는 경우
                        html += '        <img src="/images/default-image.jpg" alt="No Image" />';
                    }
                    html += '    </div>' +
                        '    <p class="brd_name">' + product.brand + '</p>' +
                        '    <p class="prd_name">' + product.model + '</p>' +
                        '    <p class="price">' + new Intl.NumberFormat('ko-KR').format(product.price) + '원</p>' +
                        '</div>';
                });

                // 생성된 HTML을 #endList 전에 추가
                $("#Infinity_product_list").before(html);

                // 로딩 중 표시 숨김
                $(".loading-indicator").hide();

                // 받은 데이터를 JSON 문자열로 변환하여 출력
                console.log(JSON.stringify(response));

                scrollPosition += 12;
        },

        error: function () {
            console.log("무한 스크롤 오류");
        }
    });
}


// IntersectionObserver 콜백 함수
function callback(entries, observer) {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            // 교차 영역에 들어왔을 때 추가 상품 로딩
            productLoad();
        }
    });
}


