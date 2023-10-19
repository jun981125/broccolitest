function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailaddress").focus();
        }
    }).open();
}

function sample6_execDaumPostcode2() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress2").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress2").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode2').value = data.zonecode;
            document.getElementById("address2").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailaddress2").focus();
        }
    }).open();
}

$(document).ready(function() {
    var modal = document.getElementById('addressModal');
    var editmodal = document.getElementById('editAddressModal');
    var openModalButton = document.getElementById('openModalButton');
    var closeModalButton = document.getElementById('closeModalButton');
    var closeModalButton2 = document.getElementById('closeEditModalButton');
    var saveButton = document.getElementById('saveButton');
    var editButtons = document.querySelectorAll('.editButton');
    var deleteButtons = document.querySelectorAll('.deleteButton');

    openModalButton.onclick = function() {
        modal.style.display = 'block';
    }

    closeModalButton.onclick = function() {
        modal.style.display = 'none';
    }

    closeModalButton2.onclick = function() {
        editmodal.style.display = 'none';
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }

    saveButton.onclick = function() {
        // 선택한 라디오 버튼의 값을 가져옵니다.
        var selectedAddressId = $("input[name='defaultAddress']:checked").val();

        // 선택한 주소를 서버로 전송합니다.
        $.ajax({
            url: '/set-default-address/' + selectedAddressId, // 서버 컨트롤러 경로로 수정
            type: 'POST',
            success: function(data) {
                // 변경 내용을 성공적으로 저장한 경우의 처리
                alert('변경 내용이 저장되었습니다.');
                // 페이지를 새로고침하거나 메시지를 표시할 수 있습니다.
            },
            error: function() {
                // 변경 내용 저장 중 오류 발생한 경우의 처리
                alert('변경 내용을 저장하는 동안 오류가 발생했습니다.');
            }
        });
    }


    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var addressId = button.getAttribute('data-addressid');
            var editForm = document.getElementById('editAddressForm');
            editForm.reset();
            console.log(addressId);

            $.ajax({
                url: '/get-address/' + addressId,
                type: 'GET',
                data: { addressid: addressId },
                success: function(data) {
                    document.getElementById('zipcode2').value = data.zipcode;
                    document.getElementById('address2').value = data.address;
                    document.getElementById('detailaddress2').value = data.detailaddress;
                    document.getElementById('sample6_extraAddress2').value = data.reference;
                    document.getElementById('editAddressId').value = data.addressid;
                    document.getElementById('editCustomernum').value = data.customernum;

                    editmodal.style.display = 'block';
                },
                error: function() {
                    alert('주소 정보를 가져오지 못했습니다.');
                }
            });
        });
    });

    document.getElementById('editSaveButton').addEventListener('click', function() {
        var editForm = document.getElementById('editAddressForm');
        var formData = new FormData(editForm);
        var customernum = document.getElementById('editCustomernum').value;

        // FormData에 customernum을 추가합니다.
        formData.append('customernum', customernum);

        $.ajax({
            url: '/update-address',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                addressid: formData.get('addressid'),
                zipcode: formData.get('zipcode'),
                address: formData.get('address'),
                detailaddress: formData.get('detailaddress'),
                reference: formData.get('reference'),
                customernum: formData.get('customernum')
            }),
            success: function(response) {
                if (response === "Success") {
                    editmodal.style.display = 'none';
                    window.location.reload();
                } else {
                    alert('주소 정보를 업데이트하지 못했습니다.');
                }
            },
            error: function() {
                alert('주소 정보를 업데이트하지 못했습니다.');
            }
        });
    });

    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var addressId = button.getAttribute('data-addressid');
            // 사용자에게 삭제 여부를 확인하는 다이얼로그 표시
            var confirmDelete = confirm('이 주소를 삭제하시겠습니까?');

            if (confirmDelete) {
                // 사용자가 확인을 클릭한 경우, 주소를 삭제합니다.
                $.ajax({
                    url: '/delete-address/' + addressId,
                    type: 'GET',
                    success: function(response) {
                        if (response === "Success") {
                            // 삭제 성공 시 페이지를 리로드합니다.
                            window.location.reload();
                        } else {
                            alert('주소 삭제에 실패했습니다.');
                        }
                    },
                    error: function() {
                        alert('주소 삭제에 실패했습니다.');
                    }
                });
            }
        });
    });

});
