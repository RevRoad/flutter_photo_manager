import 'type.dart';

class ThumbOption {
  const ThumbOption({
    required this.width,
    required this.height,
    this.format = ThumbFormat.jpeg,
    this.quality = 95,
    this.exactSize = false,
  });

  factory ThumbOption.ios({
    required int width,
    required int height,
    ThumbFormat format = ThumbFormat.jpeg,
    int quality = 95,
    bool exactSize = false,
    bool download = false,
    DeliveryMode deliveryMode = DeliveryMode.opportunistic,
    ResizeMode resizeMode = ResizeMode.fast,
    ResizeContentMode resizeContentMode = ResizeContentMode.fit,
  }) {
    return _IosThumbOption(
      width: width,
      height: height,
      format: format,
      quality: quality,
      exactSize: exactSize,
      download: download,
      deliveryMode: deliveryMode,
      resizeMode: resizeMode,
      resizeContentMode: resizeContentMode,
    );
  }

  final int width;
  final int height;
  final ThumbFormat format;
  final int quality;
  final bool exactSize;

  Map<String, Object> toMap() {
    return {
      'width': width,
      'height': height,
      'format': format.index,
      'quality': quality,
      'exactSize': exactSize,
    };
  }

  void checkAssert() {
    assert(width > 0 && height > 0, "The width and height must better 0.");
    assert(quality > 0 && quality <= 100, "The quality must between 0 and 100");
  }
}

class _IosThumbOption extends ThumbOption {
  const _IosThumbOption({
    required int width,
    required int height,
    ThumbFormat format = ThumbFormat.jpeg,
    int quality = 95,
    bool exactSize = false,
    this.download = false,
    required this.deliveryMode,
    required this.resizeMode,
    required this.resizeContentMode,
  }) : super(
          width: width,
          height: height,
          format: format,
          quality: quality,
          exactSize: exactSize,
        );

  final DeliveryMode deliveryMode;
  final ResizeMode resizeMode;
  final ResizeContentMode resizeContentMode;
  final bool download;

  @override
  Map<String, Object> toMap() {
    return <String, Object>{
      'deliveryMode': deliveryMode.index,
      'resizeMode': resizeMode.index,
      'resizeContentMode': resizeContentMode.index,
      'download': download,
    }..addAll(super.toMap());
  }
}
