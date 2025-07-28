from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.conf import settings
from django.urls import path
from django.template import Template, Context
from django.utils.html import escape
import django
import os

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'secure2')

settings.configure(
    DEBUG=True,
    SECRET_KEY='unsafe-secret-key-for-demo',
    ROOT_URLCONF='secure2',
    ALLOWED_HOSTS=['*']
)

django.setup()

@csrf_exempt
def home(request):
    return HttpResponse('''
    <form method="POST" action="/comment">
        <textarea name="comment" placeholder="Leave a comment"></textarea><br>
        <input type="submit" value="Post Comment">
    </form>
    ''')

@csrf_exempt
def comment(request):
    if request.method == 'POST':
        user_comment = request.POST.get('comment', '')
        
        escaped_comment = escape(user_comment)
        
        template = Template('''
        <h2>Your Comment:</h2>
        <div class="comment">{{ comment }}</div>
        <a href="/">Back to form</a>
        ''')
        
        context = Context({'comment': escaped_comment})
        html_response = template.render(context)
        
        return HttpResponse(html_response)
    
    return HttpResponse('Invalid request')

urlpatterns = [
    path('', home, name='home'),
    path('comment', comment, name='comment'),
]

if __name__ == '__main__':
    from django.core.management import execute_from_command_line
    execute_from_command_line(['manage.py', 'runserver'])