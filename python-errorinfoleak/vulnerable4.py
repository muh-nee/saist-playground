from django.http import HttpResponse, JsonResponse
from django.views import View


class DataView(View):
    def get(self, request):
        try:
            input_val = request.GET.get('input', '')
            result = process_input(input_val)
            return JsonResponse({'result': result})
        except Exception as e:
            return HttpResponse(str(e), status=500)


def process_input(val):
    return val.upper()
