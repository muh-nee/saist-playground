# Log injection via Rack middleware writing raw header values
class AuditMiddleware
  def initialize(app)
    @app = app
    @logger = Logger.new($stdout)
  end

  def call(env)
    request = Rack::Request.new(env)
    referer = request.env['HTTP_REFERER'] || 'none'
    # VULNERABLE — Referer header may contain CRLF sequences
    @logger.info("Incoming request referer: #{referer}")
    @app.call(env)
  end
end
