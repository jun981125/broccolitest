// 메인 슬라이더 설정
var currentPage = 2;
var totalLoadedProducts = 0; // 로드된 상품의 총 개수

$(document).ready(function () {
    $('.flexslider').flexslider({
        animation: 'slide',
        selector: '.slides > li',
        controlsContainer: ".flexsliderwrapper",
    });

    // IntersectionObserver 기본 설정
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
function productLoad() {
    // Ajax로 상품 로딩
    $.ajax({
        type: "GET",
        url: "/loadProducts",
        data: { scrollPosition: currentPage },

        success: function (response) {
            // 받은 데이터를 이용하여 동적으로 HTML 생성
            var html = '';
            response.list.forEach(function(product) {
                if (totalLoadedProducts < 24) { // 32개까지만 로드
                    html += '<div class="list_con">' +
                        '    <div class="img_container">';

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
                        '<div class="text_container">' +
                        '<h3>' +
                        '    <span class="brd_name">' + product.brand + '</span>' +
                        '    <h3 class="prd_name">' + product.model + '</h3>' +
                        '</h3>' +
                        '</div>' +
                        '    <p class="price">' + new Intl.NumberFormat('ko-KR').format(product.price) + '원</p>' +
                        '</div>';

                    totalLoadedProducts++;
                }
            });

            // 생성된 HTML을 #endList 전에 추가
            $("#Infinity_product_list").before(html);

            // 로딩 중 표시 숨김
            $(".loading-indicator").hide();
            // 받은 데이터를 JSON 문자열로 변환하여 출력
            currentPage += 1;
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

// 스크롤 이벤트에 대한 처리
$(window).scroll(function() {
    if ($(window).scrollTop() + $(window).height() == $(document).height()) {
        // 스크롤이 맨 아래에 도달했을 때 추가 상품 로딩
        productLoad();
    }
});
